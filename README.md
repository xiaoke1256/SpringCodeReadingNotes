# Spring 源码阅读笔记

&ensp;&ensp;&ensp;&ensp; 以Spring 4.2.9版本为基础,进行以下探索.

## 1.1 Spring是如何处理事务的

&ensp;&ensp;&ensp;&ensp;即@Transactional注解是如何起作用的.我们先创建一个类TestService,加上@Service和@Transactional注解,随便写一个保存数据的方法.写个测试类运行这个TestService,开始以下分析.

### 1.1.1 注册BeanDefinition的阶段

&ensp;&ensp;&ensp;&ensp;xml扫描过后，发现把所有BeanDefinition打印出来，发现有一个bean叫做“proxyTargetClass=false; optimize=false; opaque=false; exposeProxy=false; frozen=false”，他实际上是一个InfrastructureAdvisorAutoProxyCreator.


### 1.1.2 创建bean的阶段

#### (1) 是哪里把一个普通的bean变成一个代理的？

&ensp;&ensp;&ensp;&ensp;从AbstractApplicationContext.refresh()开始读，发现finishBeanFactoryInitialization()方法中处理了根据BeanDefinition创建bean的部分.
继续设置断点发现创建好的bean(TestService.class)经过AbstractAutowireCapableBeanFactory.initializeBean(final String beanName, final Object bean, RootBeanDefinition mbd)调用后变成了一个Proxy.具体调用方法是：
AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(),继而调用其子类的
InfrastructureAdvisorAutoProxyCreator.postProcessAfterInitialization().最后调用AbstractAutoProxyCreator.wrapIfNecessary()方法。

#### (2) advice是如何织入的？

&ensp;&ensp;&ensp;&ensp;wrapIfNecessary()方法中先获取全局的advice,发现有一个advice是BeanFactoryTransactionAttributeSourceAdvisor（估计是@EnableTransactionManagement注解起的作用）.现在要做的是，把BeanFactoryTransactionAttributeSourceAdvisor织入目标bean（TestService）中.
    
&ensp;&ensp;&ensp;&ensp;先判断TestService是否与BeanFactoryTransactionAttributeSourceAdvisor匹配,用AopUtils.canApply(candidate, clazz, hasIntroductions)方法判断.其中调用了AopUtils.canApply(Pointcut pc, clazz, hasIntroductions)方法,然后通过反射遍历了目标类（参数clazz）的各个方法.最终调用了AbstractFallbackTransactionAttributeSource.getTransactionAttribute(Method method, Class<?> targetClass) 方法,获取了@Trasatinal注解里的属性(如“PROPAGATION_REQUIRED,ISOLATION_DEFAULT”),如果不为空则判断为匹配。

&ensp;&ensp;&ensp;&ensp;判断为匹配就开始创建代理,调用AbstractAutoProxyCreator.createProxy(Class<?> beanClass, String beanName, Object[] specificInterceptors, TargetSource targetSource)方法.参数中specificInterceptors 为要织入的拦截器.最终创建出来的代理类是"TestService$$EnhancerBySpringCGLIB".

### 1.1.3 advice是如何增强事务处理的

&ensp;&ensp;&ensp;&ensp;现在回头看看BeanFactoryTransactionAttributeSourceAdvisor这个类.这个类中有个成员变量叫advice,他是一个TransactionInterceptor的实例.TransactionInterceptor继承了TransactionAspectSupport类,其中的方法invokeWithinTransaction(Method method, Class<?> targetClass, final InvocationCallback invocation)就是对方法进行事务上的增强处理的.继续阅读invokeWithinTransaction 方法，就很容易发现createTransactionIfNecessary,completeTransactionAfterThrowing和commitTransactionAfterReturning三个处理事务的生命周期处理方法.其中createTransactionIfNecessary方法有一个参数是tm（PlatformTransactionManager）接下来就是PlatformTransactionManager的实现类处理事务的开始,提交,回滚,挂起等动作了.
另外要注意的是invokeWithinTransaction方法中有一个参数是method,也就意味着此时还需要获取方法上的Transactional注解,根据Transactional主键的属性（隔离级别和传播方式）做一些处理.
