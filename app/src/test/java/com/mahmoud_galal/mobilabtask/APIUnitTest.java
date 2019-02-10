package com.mahmoud_galal.mobilabtask;

import com.mahmoud_galal.mobilabtask.Utils.Constants;
import static com.mahmoud_galal.mobilabtask.Utils.Constants.*;
import com.mahmoud_galal.mobilabtask.api.NetworkingManager;
import com.mahmoud_galal.mobilabtask.model.Gallery;
import com.mahmoud_galal.mobilabtask.model.Image;
import com.mahmoud_galal.mobilabtask.model.ServerResponse;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 *
 * Testing the Imgur response
 */
public class APIUnitTest {
    @Test
    public void testAPI() {
        NetworkingManager networkingManager =  new NetworkingManager(null);

        ServerResponse response = networkingManager.getAllGalleries(
                Sections.SECTIONS_HOT.getName(),
                SortOption.SORT_OPTION_VIRAL.getName(),
                WindowOption.WINDOW_OPTION_ALL.getName(),true);

        assertNotNull(response);
        assertNotNull(response.data);
        assertTrue(response.data.size()>0);
        for(Gallery item:response.data ){
            System.out.println(item.id+"");
            if(item .images != null)
            for(Image img: item.images){
                System.out.println("\t\t"+img.id+"");
            }
        }

    }
}