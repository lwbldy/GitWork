package com.lwbldy;

import com.lwbldy.gen.service.SysGeneratorService;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@SpringBootTest
class LwbAdminTinyApplicationTests {

	@Autowired
	SysGeneratorService sysGeneratorService;

	@Test
	void contextLoads()  throws IOException {

//		String[] tables = {"pms_product_category"};
//		String packageName = "com.lwbldy.mall";
//		String author = "lwbldy";
//		String email = "lwbldy@163.com";
//		String tablePrefix = null;
//		String modelName = "mall";
//
//		byte[] bytes = sysGeneratorService.generatorCode(tables,packageName,author,email,tablePrefix, modelName);
////        sysGeneratorService.generatorCode(tables,packageName);
//		OutputStream out = new FileOutputStream("d:\\test.zip");
//		IOUtils.write(bytes,out);
	}

}
