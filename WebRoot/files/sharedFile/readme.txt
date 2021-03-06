[启航聊天室]

主页：johnson.cloudplatform.hk/chatRoom

描述：启航聊天室是一个集成了单人点对点聊天和多人共享聊天室的网站

作者：杨伟

作者院校：成都信息工程大学 13级 计算机学院 应用4班

设计目标与意义：
启航聊天室基于B/S架构，全部使用web技术实现即时聊天类产品的各种功能，
网页应用的特点就是轻量化和便捷性，用户不用安装软件，只需要使用浏览器就能在各个平台随意切换。

关键技术：
该聊天室的实现主要依赖于Ajax技术，利用Ajax创建异步请求到服务器后台，
然后后台进行逻辑的处理，处理完成后将响应信息发回浏览器客户端，由客户端执行局部页面更新的操作。

作品特色：
项目实现了人人点对点聊天功能和多人共享聊天室的功能。
在多人聊天的模式下，用户能够收到所用同时在聊天室中的其它用户发送的消息，
用户能够发送表情和文字，但是不能发送敏感词汇，比如和色情暴力相关的，
这些敏感词汇可以在一个配置文件中手动插入，一旦用户发送了不良消息，其它用户收到的消息将别替换为“***”，
并且消息发送者会被系统强制下线；单人点对点聊天部分功能类似于web QQ，所有即时消息都不会被缓存到数据库，
除了如果聊天对方出于不在线状态的话会缓存最近一条消息，以便对方上线后能够收到消息提示，这样设计的原因是，
毕竟这是一个多用户的聊天程序，缓存过多的数据可能会对数据库造成不小的压力。单人聊天中用户能够查看、删除和添加好友，
可以在好友列表和设置界面进行相关的操作，用户也能够对自己的资料进行简单的修改，有一点用户个性化的意思。
在多人聊天和单人点对点聊天中，实现了用户的单态登录，即：同一个账号只能被一个用户登录使用，
如果一个已经登录的账户被其它用户登录，那么先登录的那个用户挥别强制下线，同时收到系统的警告信息。
