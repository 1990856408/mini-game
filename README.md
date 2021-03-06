## note:  

**项目版本：** jdk1.8；gradle6.3；libgdx1.61；  

**项目地址：** [Gitee](https://gitee.com/dongXuYing/jiananzhao-game) | [GitHub](https://github.com/1990856408/mini-game)  

**项目结构：**  

- core：核心模块，包含各种模板类和一些工具包；  
  - assets：资源目录 （[百度网盘](https://pan.baidu.com/s/1TwZaM-iChXekMC-266AKVg) 提取码：uy6k） 
    - members：角色  
    - menus：菜单  
    - monsters：怪物  
    - widgets：装饰  
    - sounds：音效  
    - sounds_small：音效（小）  
    - miniGameConfig.json：配置文件  

````json
{
  "screenSetting": {
    "viewRate": 100,
    "viewW": 720,
    "viewH": 540,
    "frameDuration": 0.0833
  },
  "physicalSetting": {
    "viewRate": 0.01,
    "viewW": 7.20,
    "viewH": 5.40,
    "memberViewRate": 0.005,
    "gravity": 9.8,
    "timeStep": 0.0167
  }
}
````

- custom：业务模块，为了测试核心模块而实现的一个小游戏，开发者可参考此模块内的代码，打包时可移除该模块； 

| 按键 |     功能     |
| :--: | :----------: |
|  W   | 炸弹、气功波 |
|  S   |     下蹲     |
|  A   |     左移     |
|  D   |     右移     |
|  J   |  子弹、裂脚  |
|  K   |     跳跃     |
|  I   |     变身     |
|  O   |    debug     |
|  N   |     榴弹     |
|  M   |              |

- desktop：libgdx自带的桌面运行模块；  

**项目作者：**  

- 微信：wonderzhnn  
- 邮箱：18035541373@163.com  
- [知乎](https://www.zhihu.com/people/nan-gua-7hao) | [简书](https://www.jianshu.com/u/f87f95e4c3f4) | [CSDN](https://blog.csdn.net/qq_35045184)  
- 公众号：以镒称铢（zhaojn\_）  
  ![公众号二维码](https://jiananzhao.oss-cn-hongkong.aliyuncs.com/mini-game/%E4%BA%8C%E7%BB%B4%E7%A0%81.jpg)  

**项目说明：**   

- 该项目是基于[libgdx](https://libgdx.badlogicgames.com/)做二次开发的，目前主要针对2D横版冒险、格斗类游戏做二次封装，定义通用的模板，细化生命周期，提供各种丰富的工具，让开发者能够快速地制作一款游戏；  
- 该项目仍然保持跨平台的特性，不过目前为了方便而将android，ios，html等模块都暂时干掉了。如果你已经完成了一款游戏需要在其他平台使用，可联系作者帮你完成打包；  
- 该项目clone到本地即可运行，详细API文档请关注公众号，回复 "MiniGame" 阅读，在开发过程中若遇到问题可联系作者；  

**项目展示：** （若图片不能显示，请移至[Gitee](https://gitee.com/dongXuYing/jiananzhao-game)观看）  

***

__2020-06-07（轨迹渲染）__  
- <u>环形轨迹，环绕自身</u>  
![环形轨迹](https://jiananzhao.oss-cn-hongkong.aliyuncs.com/mini-game/%E8%BD%A8%E8%BF%B9%E6%B8%B2%E6%9F%931%20.gif)  
- <u>混合轨迹，组合两个线性运动</u>  
![混合轨迹](https://jiananzhao.oss-cn-hongkong.aliyuncs.com/mini-game/%E8%BD%A8%E8%BF%B9%E6%B8%B2%E6%9F%932.gif)  

***

__2020-06-01__  
- <u>马里奥变身春丽，裂脚～气功波</u>  
![马里奥变身春丽，裂脚～气功波](https://jiananzhao.oss-cn-hongkong.aliyuncs.com/mini-game/57D4CECE-85B1-4252-B3F6-655D2BCBCDCA.gif)  

***

__持续更新__  


