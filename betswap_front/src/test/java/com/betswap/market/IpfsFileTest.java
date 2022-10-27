package com.betswap.market;
import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class IpfsFileTest {

    @Test
    public void testDownload() throws IOException {

        String type = ".txt";
        String fileHash = "QmbYBPQxNoAqGPsA2m9CwaPuXq9rqypPhcLvmAaPJ4UK14";

        IPFS ipfs = new IPFS("/ip4/39.106.87.106/tcp/5009");

        //下载文件
        Multihash filePointer = Multihash.fromBase58(fileHash);//参数为文件 hash
        byte[] fileContents = ipfs.cat(filePointer);

        //保存文件
        File downloadfile = new File("/Users/huyueyang/Desktop/hu.txt");

        if(!downloadfile.exists()) {
            downloadfile.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(downloadfile);
        fop.write(fileContents);
        fop.flush();
        fop.close();
    }
}
