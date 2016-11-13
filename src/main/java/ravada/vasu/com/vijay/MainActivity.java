package ravada.vasu.com.vijay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);
        Log.e("VR","Inserting");
        RepositoryInfo repositoryInfo = new RepositoryInfo();
        repositoryInfo.setName("Vasu");
        repositoryInfo.setUrl("www.vasuravada.com");
        repositoryInfo.setDescription("Testing DB");
        db.addRepositoryInfo(repositoryInfo);
        Log.e("VR","Reading");
        List<RepositoryInfo> repositoryInfoList = db.getAllRepositoriesInfo();
        for (RepositoryInfo repositoryInfo1: repositoryInfoList){
            String log ="Id: "+ repositoryInfo1.getId()+"\n"+
                    "Name: "+ repositoryInfo1.getName()+"\n"+
                    "Url: "+ repositoryInfo1.getUrl()+"\n"+
                    "Description: "+ repositoryInfo1.getDescription();
            Log.e("VR",log);
        }
    }
}
