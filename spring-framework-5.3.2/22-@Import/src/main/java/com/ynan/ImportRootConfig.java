package com.ynan;

import com.ynan.service.DeferredImport;
import com.ynan.service.RegistrarImport;
import com.ynan.service.SelectorImport;
import com.ynan.service.SimpleImport;
import org.springframework.context.annotation.Import;

/**
 * @author yuannan
 * @date 2022/11/16 20:31
 */
//@Import(SimpleImport.class)
//@Import(RegistrarImport.class)
//@Import(SelectorImport.class)
@Import(DeferredImport.class)
public class ImportRootConfig {


}
