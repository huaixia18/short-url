package com.huaixia.shorturl;

import com.google.common.hash.Hashing;
import com.huaixia.shorturl.utils.DecimalToBase62Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShortUrlApplicationTests {

    @Test
    void contextLoads() {
        String url = "https://www.yuque.com/snailclimb/mf2z3k/kochkf";
        long l = Hashing.murmur3_32_fixed().hashUnencodedChars(url).padToLong();
        String hexString = Long.toHexString(l);
        String base62 = DecimalToBase62Util.decimalToBase62(l);
        System.out.println("hexString:" + hexString);
        System.out.println("base62:" + base62);
    }

}
