/**
 * 
 */
package android.location;

import java.util.List;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.RemoteException;

import com.android.internal.location.ProviderProperties;

/**
 * @author steven.lam.t.f
 *
 */
public interface ILocationManager{
	public abstract class Stub implements ILocationManager{
	}
	void requestLocationUpdates(LocationRequest request, ILocationListener listener,
            PendingIntent intent, String packageName) throws RemoteException;
    void removeUpdates(ILocationListener listener, PendingIntent intent, String packageName) throws RemoteException;

    void requestGeofence(LocationRequest request, Geofence geofence,
            PendingIntent intent, String packageName) throws RemoteException;
    void removeGeofence(Geofence fence, PendingIntent intent, String packageName) throws RemoteException;

    Location getLastLocation(LocationRequest request, String packageName) throws RemoteException;

    boolean addGpsStatusListener(IGpsStatusListener listener, String packageName) throws RemoteException;
    void removeGpsStatusListener(IGpsStatusListener listener) throws RemoteException;

    boolean geocoderIsPresent();
    String getFromLocation(double latitude, double longitude, int maxResults,
        GeocoderParams params, List<Address> addrs) throws RemoteException;
    String getFromLocationName(String locationName,
        double lowerLeftLatitude, double lowerLeftLongitude,
        double upperRightLatitude, double upperRightLongitude, int maxResults,
        GeocoderParams params, List<Address> addrs) throws RemoteException;

    boolean sendNiResponse(int notifId, int userResponse) throws RemoteException;

    // --- deprecated ---
    List<String> getAllProviders() throws RemoteException;
    List<String> getProviders(Criteria criteria, boolean enabledOnly) throws RemoteException;
    String getBestProvider(Criteria criteria, boolean enabledOnly) throws RemoteException;
    boolean providerMeetsCriteria(String provider, Criteria criteria) throws RemoteException;
    ProviderProperties getProviderProperties(String provider) throws RemoteException;
    boolean isProviderEnabled(String provider) throws RemoteException;

    void addTestProvider(String name, ProviderProperties properties) throws RemoteException;
    void removeTestProvider(String provider) throws RemoteException;
    void setTestProviderLocation(String provider, Location loc) throws RemoteException;
    void clearTestProviderLocation(String provider) throws RemoteException;
    void setTestProviderEnabled(String provider, boolean enabled) throws RemoteException;
    void clearTestProviderEnabled(String provider) throws RemoteException;
    void setTestProviderStatus(String provider, int status, Bundle extras, long updateTime) throws RemoteException;
    void clearTestProviderStatus(String provider) throws RemoteException;

    boolean sendExtraCommand(String provider, String command, Bundle extras) throws RemoteException;

    // --- internal ---

    // Used by location providers to tell the location manager when it has a new location.
    // Passive is true if the location is coming from the passive provider, in which case
    // it need not be shared with other providers.
    void reportLocation(Location location, boolean passive) throws RemoteException;

    // for reporting callback completion
    void locationCallbackFinished(ILocationListener listener) throws RemoteException;

	/*
	 * Steven
	 */
	List<String> getActualProviders() throws RemoteException;
	Location getActualLocation(String provider) throws RemoteException;
	Location getMockLocation() throws RemoteException;
	void setMockLocation(Location location) throws RemoteException;
	boolean isMocking() throws RemoteException;
	void setMocking(boolean enabled) throws RemoteException;
	boolean isLogging() throws RemoteException;
	void setLogging(boolean enabled) throws RemoteException;
	Location getLastGoodLocation() throws RemoteException;
	Location applyMock(Location location) throws RemoteException;

}
