/**
 *  @version 1.0
 * COPYRIGHTS COPELABS/ULHT, LGPLv3.0, 2017-02-14
 * Simple Dialog to Add a route to the ForwardingDaemon's RIB.
 * @author Seweryn Dynerowicz (COPELABS/ULHT)
 */

package pt.ulusofona.copelabs.ndn.android.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import pt.ulusofona.copelabs.ndn.R;
import pt.ulusofona.copelabs.ndn.android.models.Face;
import pt.ulusofona.copelabs.ndn.android.umobile.ForwardingDaemon;

public class AddRoute extends DialogFragment {
    private ForwardingDaemon mDaemon;
	private View mDialog;

	private EditText mPrefix;
	private Spinner mFaces;

	public AddRoute() {}

    public AddRoute(ForwardingDaemon fd) {
        mDaemon = fd;
    }

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		mDialog = View.inflate(getContext(), R.layout.dialog_add_route, null);

		mPrefix = (EditText) mDialog.findViewById(R.id.prefix);
		mFaces = (Spinner) mDialog.findViewById(R.id.faces);
        List<String> spinnerList = new ArrayList<>();
        for(Face current : mDaemon.getFaceTable())
            spinnerList.add(Long.toString(current.getId()));
        mFaces.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerList));

		return builder
			.setView(mDialog)
			.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface di, int id) {
				String host = mPrefix.getText().toString();
				String faceId = mFaces.getSelectedItem().toString();
				if(host.isEmpty())
					host = getString(R.string.defaultPrefix);
				if(faceId.isEmpty())
					faceId = "0";
				mDaemon.addRoute(host, Long.decode(faceId), 0L, 0L, 1L);
				}
			})
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface di, int id) {
					getDialog().cancel();
				}
			})
			.create();
	}
}