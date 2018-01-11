package nyc.c4q.androidtest_unit4final;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ColorAdapter adapter;
    protected HashMap<String, String> colorDict;
    protected List<String> colorsList;
    private String black;
    private String brown;
    private String orange;
    private String purple;
    private ColorModel colorModel = new ColorModel();
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retroStuff();

        colorDict = new HashMap<>();
        colorDict.put("indigo", "#4b0082");
        colorDict.put("green", "#00ff00");
        colorDict.put("blue", "#0000ff");
        colorDict.put("red", "#ff0000");
        colorDict.put("black", black);
        colorDict.put("brown", brown);
        colorDict.put("orange", orange);
        colorDict.put("purple", purple);

        // TODO: adding all the colors and their values would be tedious, instead fetch it from the url below +++
        // https://raw.githubusercontent.com/operable/cog/master/priv/css-color-names.json

        colorsList = new ArrayList<>();
        String[] names = new String[] {"blue", "red", "purple", "indigo", "orange", "brown", "black", "green"};
        for(String n: names) colorsList.add(n);

        recyclerView = findViewById(R.id.rv);
        adapter = new ColorAdapter(colorsList, colorDict);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // TODO: Add options menu with the item "Info" which is always visible ++++++
    // TODO: When "Info" menu item is clicked, display the fragment InfoFragment
    // TODO: If InfoFragment is already visible and I click "Info", remove InfoFragment from the view. ++++
    // Link to creating options menu: https://github.com/C4Q/AC-Android/tree/v2/Android/Lecture-9-Menus-and-Navigation#anatomy-of-menu-xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_info, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.info_item:
                clickInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void clickInfo(){
        LinearLayout fragmentContainer = findViewById(R.id.fragment_container);

        if(fragmentContainer.getVisibility() == View.VISIBLE){
            fragmentContainer.setVisibility(View.GONE);
        }
        else{
            fragmentContainer.setVisibility(View.VISIBLE);
        }
        }
    public void retroStuff(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ColorService colorService = retrofit.create(ColorService.class);
        Call<ColorModel> bookModelCall = colorService.getColors();
        bookModelCall.enqueue(new Callback<ColorModel>() {
            @Override
            public void onResponse(Call<ColorModel> call, Response<ColorModel> response) {

                black = response.body().getBlack();
                brown = response.body().getBrown();
                orange = response.body().getOrange();
                purple = response.body().getPurple();
                colorDict.put("black", black);
                colorDict.put("brown", brown);
                colorDict.put("orange", orange);
                colorDict.put("purple", purple);
                Log.e("hello", purple);
                Log.e("BLACK", black.toString());

            }

            @Override
            public void onFailure(Call<ColorModel> call, Throwable t) {
                Log.e("Fail", "I am on onFailure" + t.toString());
            }
        });
    }
}
