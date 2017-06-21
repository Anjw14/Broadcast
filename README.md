# Broadcast
Java exercise

### 程序说明

- announcement.properties		为配置文件；
- MainServer.java			为服务器主程序；
- VisibleClient.java		客户端主程序；

### 实现功能

- 假设每个班级有个信息汇总和发送的路由器。班级管理员可以向此路由器发送特定的班级活动安排信息。这些信息可以用特定的文本形式进行描述，例如类似于E格式的文件，或是properties类型的文件。考虑到日常学习工作所需，班级管理员希望能够定义一个文件，描述班级集体活动信息，如（可以是多条信息）startTime=2017年5月27日上午9:00 endTime=2017年5月27日上午10:00，location=新水303，activity=开班会。然后，通过网络广播的方式发送给班级中所有同学。每个同学自己有一个客户端（一个闹钟程序），加入班级广播地址后，监听相关信息，并实时更新到自己的软件中，定时提醒自己（可以考虑提醒的时间比开始的时间早半个小时，或者15分钟）。考虑到，班级活动需要持续更新，所有客户端可以随时接入广播网络，或退出，而路由器功能不受其影响。

- 路由器端程序；
1. 无需界面；
2. 可以轮询读取一个目录中的文件信息（每隔30秒，读取特定目录中的一个文件内容）；
3. 若文件内容有更新，则通过广播方式向外发送更新后的活动信息；
4. 可以接受客户端独立、直接的socket连接请求，连接成功后，向客户端发送最新活动信息；然后，关闭和客户端的socket通信联系；

- 客户端应用程序，要求：
1. 实现一个小闹钟的功能，具有数字形式的当前时间显示，并可设定闹钟时刻，在到达闹钟时刻后给出明显提示；可以调整当前时间，按照小时、分、秒分别调整；输入一个以上的闹钟时刻，触发报警提示；报警提示基本形式是以对话框弹出报警，用户可以设定报警文字；
2. 具有网络通信功能，可以通过连接路由器（地址已知），获取最新活动信息，并更新本地闹钟设定；也可以处于广播监听状态，得到广播信息后，自动更新本地闹钟设定；
