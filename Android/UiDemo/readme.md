# 实现一个UI布局

* 左边红色区域宽度340个像素，右边白色区域宽度为600个像素，图片整体高度为250个像素
* 左边金额数字宽度为250个像素，高度为60个像素，该数字不能折行，需要自适应大小，比如金额是800000.00
* 左边金额数字上边距60个像素，左、右边距20个像素；【点击领取】上边距32个像素，左右边距20个像素，下边距60个像素
* 右边白色区域内的文字不能折行，字数超出则显示黑点
* 右边白色区域第一行【2016年7月百度推广优..】上边距60个像素，左右边距30个像素；第二行有效期的内容上边距32个像素，左右边距30个像素，下边距80个像素
* demo 中金额数字设为800000.00需要考虑不同屏幕的适配

  如果纯粹依靠 px 来画界面，屏幕适配性很不好，也不符合 Google 官方文档里的要求。  
  如果换用 dp，就能达成适配的效果，如下图，在不同分辨率下都能保持一致的显示效果。
  
<img src="https://github.com/Zhai-Wang/BaiduIfeDemo/blob/master/Android/UiDemo/screenshot/1.png">
<img src="https://github.com/Zhai-Wang/BaiduIfeDemo/blob/master/Android/UiDemo/screenshot/2.png">
<img src="https://github.com/Zhai-Wang/BaiduIfeDemo/blob/master/Android/UiDemo/screenshot/3.png">
