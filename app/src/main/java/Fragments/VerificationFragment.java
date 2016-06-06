package Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.holler.hollerapp.LocationActivity;
import com.holler.hollerapp.R;

/**
 * Created by rakeshkoplod on 10/05/16.
 */
public class VerificationFragment extends android.support.v4.app.Fragment {

    private Spinner spinnerIdType;
    private Button addFileButton;

    public VerificationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_verification, container, false);
        //RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
        //Drawable drawable = ratingBar.getProgressDrawable();
        //drawable.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        //ratingBar.setRating(3);

        spinnerIdType = (Spinner) rootView.findViewById(R.id.id_spinner);
        String[] IdItems = new String[] { "Voter ID", "Driving License","PAN Card","Adhaar Card"};
        ArrayAdapter<String> idAdapter = new ArrayAdapter<String>(this.getActivity(),
                R.layout.spinner_item, IdItems);

        spinnerIdType.setAdapter(idAdapter);

        addFileButton = (Button) rootView.findViewById(R.id.buttonAddFile);
        addFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfilePicture();
            }
        });

        return rootView;
    }

    private void changeProfilePicture()
    {
       /* Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent,"Select file to upload "), 0);*/

        final CharSequence options[] = new CharSequence[] {"Take Photo", "Choose from Library", "Cancel"};

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this.getActivity());
        builder.setCancelable(false);
        builder.setTitle("Select your option:");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                // the user clicked on options[which]
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                } else if (options[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }
}
