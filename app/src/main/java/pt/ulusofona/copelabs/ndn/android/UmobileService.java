/**
 *  @version 1.0
 * COPYRIGHTS COPELABS/ULHT, LGPLv3.0, 2017-02-21
 * The UmobileService represents a device running the NDN-Opp platform mostly intended for demo and testing purposes.
 * @author Seweryn Dynerowicz (COPELABS/ULHT)
 */
package pt.ulusofona.copelabs.ndn.android;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import pt.ulusofona.copelabs.ndn.R;
import pt.ulusofona.copelabs.ndn.android.ui.fragment.Table;

public class UmobileService implements Table.Entry {
    public enum Status {
        AVAILABLE("Av"),
        UNAVAILABLE("Un");
		private String symbol;
		Status(String s) { symbol = s; }
		public String toString() { return symbol; }
    }

	public Status currently;
    public String uuid;
	public String host;
	public int port;

    public UmobileService() {}

    public UmobileService(UmobileService original) {
        currently = original.currently;
        uuid = original.uuid;
        host = original.host;
        port = original.port;
    }

    public UmobileService(Status s, String n, String h, int p) {
		currently = s;
        uuid = n;
		host = h;
		port = p;
	}

    public Status getStatus() {
        return currently;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        UmobileService that = (UmobileService) other;
        return this.currently == that.currently
                && this.uuid.equals(that.uuid)
                && this.host.equals(that.host)
                && this.port == that.port;
    }

    @Override
    public String toString() {
        return "UmobileService{" +
                "currently=" + currently +
                ", uuid='" + uuid + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
	public View getView(LayoutInflater inflater) {
        View entry = inflater.inflate(R.layout.item_service, null, false);
        setViewContents(entry);
        return entry;
    }

    @Override
    public void setViewContents(View entry) {
        ((TextView) entry.findViewById(R.id.status)).setText(this.currently.toString());
        ((TextView) entry.findViewById(R.id.host)).setText(this.host);
        ((TextView) entry.findViewById(R.id.port)).setText(String.format(Locale.getDefault(), "%d", this.port));
        ((TextView) entry.findViewById(R.id.name)).setText(this.uuid);
    }
}