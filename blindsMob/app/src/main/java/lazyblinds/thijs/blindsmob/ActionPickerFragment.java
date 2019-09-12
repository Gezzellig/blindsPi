package lazyblinds.thijs.blindsmob;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class ActionPickerFragment extends DialogFragment {

    private EditText mEditText;

    public ActionPickerFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ActionPickerFragment newInstance(String title) {
        ActionPickerFragment frag = new ActionPickerFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.action_picker_fragment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString("title");
        getDialog().setTitle(title);
        initializeButtons(view);
    }

    private void initializeButtons(View view) {
        Button tUp = (Button) view.findViewById(R.id.tUp);
        tUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("actionPicker", "Up");
                TimedCommandBuilderHolder.gettcb().actionChosenUp();
                dismiss();
            }
        });

        Button tDown = (Button) view.findViewById(R.id.tDown);
        tDown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TimedCommandBuilderHolder.gettcb().actionChosenDown();
                dismiss();
            }
        });
    }
}