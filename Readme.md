#FastChoose Mobile APP (eBay Section)

#### An android application can enable users search  items directly what they want online.


Developed it by Java language and Android Studio.

It also provides a server-side to dispatch the Http request and Ajax call



#### <font color='red'> All the image assests, including pictures, logo, and icons,  belong to ebay, and all resources are only allowed to used by studying purpose, never bussniess. </font>



## Introduction:

#### Client Side (Android):

1.These are the major activities.



<img src = 'https://ftp.bmp.ovh/imgs/2020/07/44e8d13d7714cb20.png' width=250/>





<img src = 'https://ftp.bmp.ovh/imgs/2020/07/6549260ec153869e.png' width=250 />

<img src = 'https://ftp.bmp.ovh/imgs/2020/07/612f20938910a811.png' width=250 />



2.  It also supports more details, for example, splash screen, refresh, error validation.

<img src = 'https://ftp.bmp.ovh/imgs/2020/07/54b0603560ffe68d.png' width=800/>

<img src = 'https://ftp.bmp.ovh/imgs/2020/07/ea17e063bdad1281.png' width=800/>
<img src = 'https://ftp.bmp.ovh/imgs/2020/07/96b0d25c457d66de.png' width=800/>
<img src = 'https://ftp.bmp.ovh/imgs/2020/07/f3c43bc4b6276be4.png' width=800/>



<hr>

#### Server Side (NodeJS)

I use NodeJs to receive mobile side's Ajax call and send request to eBay.

Finally, the server side will receive  Json data from eBay. 

And after processed by server side, it will response the mobile-client side required and formatted json data.





## Quick Start

#### 1. dependencies:

##### Java SDK: 12

##### Android API:21, maybe 18 is OK.

##### Android gradle:

​    a. Volley: it helps us connect the android app with server side.

​	b. Glide :it is also powerful image downloading and caching library for Android. 

​    c.  Picasso Picasso is a powerful image downloading and caching library for Android. 

##### NodeJs: express,  cors,  got.



#### 2. Quick Start:

a. Download the codes



b. Import the android files to your android studio projects

##### 	Do not forget to add packages 



c. I already provide all materials to you, including the pictures, icons, and of course, source code.



#### WHAT??   you say that you don't know how to run it? Please google it...





## Deployment on GCP

The backend is actually deployed on google cloud platform (GCP)

So, every one can simply uses my app.ysml file to deploy the app.js to the GCP.
