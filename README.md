Material Desgin中加入的底部导航栏BottomNavigationBar的简单例子

关于Google对该控件的使用介绍：https://www.google.com/design/spec/components/bottom-navigation.html
BottomNavigationBar在github上的地址:https://github.com/Ashok-Varma/BottomNavigation

使用过程中遇到的一点小问题：

    ①setBackgroundStyle()，该方法要在设置颜色(setActiveColor() , setInActiveColor(), setBarBackgroundColor() 方法之前执行)，因为style不同颜色设置的地方也不一样。

        ②inactive color 都是代表未选中的tab的颜色。
          当style为 BACKGROUND_STYLE_STATIC的时候：active color 代表的是选中的tab的颜色，
background color 代表的是导航栏的背景色。
          当style为 BACKGROUND_STYLE_RIPPLE的时候 ： active color 代表的是导航栏的背景色，
background color 代表的是选中时的tab的颜色
