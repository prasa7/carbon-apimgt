package org.wso2.carbon.apimgt.hostobjects;

import org.jaggeryjs.hostobjects.file.FileHostObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.FileInputStream;
import java.util.zip.ZipInputStream;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TenantManagerHostObject.class})
public class TenantManagerHostObjectTest {
    private TenantManagerHostObject tmhostObject = new TenantManagerHostObject();
    @Test
    public void getStoreTenantThemesPath() throws Exception {
        Assert.assertEquals(TenantManagerHostObject.getStoreTenantThemesPath(), "repository/deployment/server/" +
                "jaggeryapps/store/site/tenant_themes/");
    }

    @Test
    public void getClassName() throws Exception {
        Assert.assertEquals(tmhostObject.getClassName(), "APIManager");
    }

    @Test
    public void jsConstructor() throws Exception {
        //TenantManagerHostObject tmHostObject = PowerMockito.
    }

    @Test
    public void jsFunction_addTenantTheme() throws Exception {
        FileHostObject fileHostObject = Mockito.mock(FileHostObject.class);
        FileInputStream inputStream = Mockito.mock(FileInputStream.class);
        ZipInputStream zipInputStream = Mockito.mock(ZipInputStream.class);
        Object args[] = {fileHostObject, "b"};

        Mockito.when(fileHostObject.getInputStream()).thenReturn(inputStream);

        PowerMockito.whenNew(ZipInputStream.class).withAnyArguments().thenReturn(zipInputStream);
        tmhostObject.jsFunction_addTenantTheme(null, null, args, null );



    }

}