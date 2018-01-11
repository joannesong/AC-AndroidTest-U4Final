package nyc.c4q.androidtest_unit4final;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by justiceo on 1/9/18.
 */

public class InfoFragment extends Fragment {

    private TextView text;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.info_fragment, container, false);
        text = v.findViewById(R.id.more_textView);
        text.setVisibility(View.INVISIBLE);
        button = v.findViewById(R.id.button_more);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreButtonClicked();
            }
        });

        return v;
    }

    public void moreButtonClicked(){
        text.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
    }
}
