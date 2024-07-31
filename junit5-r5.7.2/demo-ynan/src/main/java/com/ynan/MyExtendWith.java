package com.ynan;

import org.junit.jupiter.api.extension.*;

public class MyExtendWith implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {
    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        System.out.println(" ###### MyExtendWith : afterAll ###### ");
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        System.out.println(" ###### MyExtendWith : beforeAll ###### ");
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        System.out.println(" ###### MyExtendWith : afterEach ###### ");
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        System.out.println(" ###### MyExtendWith : beforeEach ###### ");
    }
}
