这里是自定义redisTokenStore序列化策略

因为oauth2默认的redis序列化策略是JdkSerializationStrategy，这使得利用redis里面的值看起来就像是乱码

此外，在反序列化时也会报错

故而自定义序列化策略，代码来自网路，参考https://www.cnblogs.com/meow-world/articles/15192758.html

有待学习！！！！