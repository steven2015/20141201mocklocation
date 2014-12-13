/**
 *
 */
package steven.sth;

import java.util.HashMap;
import java.util.Map;

import android.location.Location;
import android.location.LocationManager;

/**
 * @author steven.lam.t.f
 *
 */
public class S20141201MockLocationTest{
	private static final long MAXIMUM_GPS_INTERVAL = 10000;
	private static final long MINIMUM_NO_GPS_TIME = 5000;
	private final Map<String, Location> actualLocations = new HashMap<String, Location>();
	private boolean smoothing = false;
	private boolean mocking = false;
	private Location mockLocation = null;
	private Location lastLocation = null;
	private Location previousGpsLocation = null;
	private Location lastGps = null;
	private Location lastNetwork = null;
	private String lastProvider = null;

	public static final void main(final String[] args){
		new S20141201MockLocationTest().test();
	}
	private void test(){
		{
			// normal, has signal
			final long time = System.currentTimeMillis();
			this.setup(time);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345767, 114.206084, time + 1000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345777, 114.206084, time + 2000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345787, 114.206084, time + 3000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345797, 114.206084, time + 4000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345807, 114.206084, time + 5000);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345807, 114.207084, time + 5000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345817, 114.206084, time + 6000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345827, 114.206084, time + 7000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345837, 114.206084, time + 8000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345847, 114.206084, time + 9000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345857, 114.206084, time + 10000);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345857, 114.207084, time + 10000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345867, 114.206084, time + 11000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345877, 114.206084, time + 12000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345887, 114.206084, time + 13000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345897, 114.206084, time + 14000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345907, 114.206084, time + 15000);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345907, 114.207084, time + 15000);
			this.printResult("normal, has signal");
		}
		{
			// normal, no signal
			final long time = System.currentTimeMillis();
			this.setup(time);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345807, 114.207084, time + 5000);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345857, 114.207084, time + 10000);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345907, 114.207084, time + 15000);
			this.printResult("normal, no signal");
		}
		{
			// mocking, has signal
			final long time = System.currentTimeMillis();
			this.setup(time);
			this.mocking = true;
			this.mockLocation = new Location("hello");
			this.mockLocation.setLatitude(22);
			this.mockLocation.setLongitude(114);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345767, 114.206084, time + 1000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345777, 114.206084, time + 2000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345787, 114.206084, time + 3000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345797, 114.206084, time + 4000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345807, 114.206084, time + 5000);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345807, 114.207084, time + 5000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345817, 114.206084, time + 6000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345827, 114.206084, time + 7000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345837, 114.206084, time + 8000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345847, 114.206084, time + 9000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345857, 114.206084, time + 10000);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345857, 114.207084, time + 10000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345867, 114.206084, time + 11000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345877, 114.206084, time + 12000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345887, 114.206084, time + 13000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345897, 114.206084, time + 14000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345907, 114.206084, time + 15000);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345907, 114.207084, time + 15000);
			this.printResult("mocking, has signal");
		}
		{
			// mocking, no signal
			final long time = System.currentTimeMillis();
			this.setup(time);
			this.mocking = true;
			this.mockLocation = new Location("hello");
			this.mockLocation.setLatitude(22);
			this.mockLocation.setLongitude(114);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345807, 114.207084, time + 5000);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345857, 114.207084, time + 10000);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345907, 114.207084, time + 15000);
			this.printResult("mocking, no signal");
		}
		{
			// smoothing, has signal
			final long time = System.currentTimeMillis();
			this.setup(time);
			this.smoothing = true;
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345767, 114.206084, time + 1000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345777, 114.206084, time + 2000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345787, 114.206084, time + 3000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345797, 114.206084, time + 4000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345807, 114.206084, time + 5000);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345807, 114.207084, time + 5000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345817, 114.206084, time + 6000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345827, 114.206084, time + 7000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345837, 114.206084, time + 8000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345847, 114.206084, time + 9000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345857, 114.206084, time + 10000);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345857, 114.207084, time + 10000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345867, 114.206084, time + 11000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345877, 114.206084, time + 12000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345887, 114.206084, time + 13000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345897, 114.206084, time + 14000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345907, 114.206084, time + 15000);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345907, 114.207084, time + 15000);
			this.printResult("smoothing, has signal");
		}
		{
			// smoothing, no signal
			final long time = System.currentTimeMillis();
			this.setup(time);
			this.smoothing = true;
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345767, 114.206084, time + 1000);
			this.reportLocation(LocationManager.GPS_PROVIDER, 22.345777, 114.206084, time + 2000);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345807, 114.207084, time + 5000);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345857, 114.207084, time + 10000);
			this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345907, 114.207084, time + 15000);
			this.printResult("smoothing, no signal");
		}
	}
	private void setup(final long time){
		this.actualLocations.clear();
		this.smoothing = false;
		this.mocking = false;
		this.mockLocation = null;
		this.lastLocation = null;
		this.previousGpsLocation = null;
		this.reportLocation(LocationManager.GPS_PROVIDER, 22.344667, 114.206084, time - 9000);
		this.reportLocation(LocationManager.GPS_PROVIDER, 22.344677, 114.206084, time - 8000);
		this.reportLocation(LocationManager.GPS_PROVIDER, 22.344687, 114.206084, time - 7000);
		this.reportLocation(LocationManager.GPS_PROVIDER, 22.344697, 114.206084, time - 6000);
		this.reportLocation(LocationManager.GPS_PROVIDER, 22.344707, 114.206084, time - 5000);
		this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345707, 114.207084, time - 5000);
		this.reportLocation(LocationManager.GPS_PROVIDER, 22.344717, 114.206084, time - 4000);
		this.reportLocation(LocationManager.GPS_PROVIDER, 22.344727, 114.206084, time - 3000);
		this.reportLocation(LocationManager.GPS_PROVIDER, 22.344737, 114.206084, time - 2000);
		this.reportLocation(LocationManager.GPS_PROVIDER, 22.344747, 114.206084, time - 1000);
		this.reportLocation(LocationManager.GPS_PROVIDER, 22.344757, 114.206084, time);
		this.reportLocation(LocationManager.NETWORK_PROVIDER, 22.345757, 114.207084, time);
	}
	private void printResult(final String message){
		System.out.println(message);
		System.out.println("actual GPS:            " + this.actualLocations.get(LocationManager.GPS_PROVIDER));
		System.out.println("actual network:        " + this.actualLocations.get(LocationManager.NETWORK_PROVIDER));
		System.out.println("mock location:         " + this.mockLocation);
		System.out.println("last location:         " + this.lastLocation);
		System.out.println("previous gps location: " + this.previousGpsLocation);
		System.out.println("report GPS:       " + (LocationManager.GPS_PROVIDER.equals(this.lastProvider) ? "*" : " ") + "    " + this.lastGps);
		System.out.println("report network:   " + (LocationManager.NETWORK_PROVIDER.equals(this.lastProvider) ? "*" : " ") + "    " + this.lastNetwork);
		System.out.println();
	}
	private void reportLocation(final String provider, final double latitude, final double longitude, final long time){
		final Location location = new Location(provider);
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		location.setTime(time);
		if(LocationManager.GPS_PROVIDER.equals(provider)){
			location.setAccuracy(3);
		}else{
			location.setAccuracy(100);
		}
		this.overwriteLocation(location, false);
		if(LocationManager.GPS_PROVIDER.equals(provider)){
			this.lastGps = location;
		}else{
			this.lastNetwork = location;
		}
		this.lastProvider = provider;
	}
	private void overwriteLocation(final Location location, final boolean passive){
		if(passive == false){
			// save actual location
			location.setIsFromMockProvider(false);
			if(LocationManager.GPS_PROVIDER.equals(location.getProvider())){
				this.previousGpsLocation = this.actualLocations.get(LocationManager.GPS_PROVIDER);
			}
			this.actualLocations.put(location.getProvider(), new Location(location));
			// overwrite accuracy
			if(location.getAccuracy() > 40){
				location.setAccuracy((int)(30 + Math.random() * 10));
			}
			// mock / smooth
			if(this.mocking && this.mockLocation != null){
				location.setLatitude((float)(this.mockLocation.getLatitude() + 0.00005 * (Math.random() - 0.5)));
				location.setLongitude((float)(this.mockLocation.getLongitude() + 0.00005 * (Math.random() - 0.5)));
				location.setAccuracy((int)(10 + location.distanceTo(this.mockLocation)));
				if(this.lastLocation != null){
					final long duration = location.getTime() - this.lastLocation.getTime();
					if(duration > 0){
						location.setSpeed((int)(location.distanceTo(this.lastLocation) * 1000 / duration));
					}
					if(location.hasBearing()){
						location.setBearing(this.lastLocation.bearingTo(location));
					}
				}
				if(location.hasAltitude()){
					location.setAltitude((int)(location.getAltitude() + Math.random() * 2));
				}
				this.lastLocation = new Location(location);
			}else if(this.smoothing){
				if(LocationManager.GPS_PROVIDER.equals(location.getProvider())){
					this.lastLocation = new Location(location);
				}else if(this.lastLocation != null){
					final Location gpsLocation = this.actualLocations.get(LocationManager.GPS_PROVIDER);
					if(gpsLocation != null && this.previousGpsLocation != null && gpsLocation.getTime() - this.previousGpsLocation.getTime() < S20141201MockLocationTest.MAXIMUM_GPS_INTERVAL && location.getTime() - gpsLocation.getTime() > S20141201MockLocationTest.MINIMUM_NO_GPS_TIME){
						// activate smoothing if not receiving gps signal
						long deltaGpsTime = gpsLocation.getTime() - this.previousGpsLocation.getTime();
						if(deltaGpsTime == 0){
							deltaGpsTime = 1000;
						}
						final long deltaLocationTime = location.getTime() - gpsLocation.getTime() - S20141201MockLocationTest.MINIMUM_NO_GPS_TIME;
						final double deltaLatitude = (gpsLocation.getLatitude() - this.previousGpsLocation.getLatitude()) * 1000 / deltaGpsTime * (location.getTime() - this.lastLocation.getTime()) / deltaLocationTime;
						final double deltaLongitude = (gpsLocation.getLongitude() - this.previousGpsLocation.getLongitude()) * 1000 / deltaGpsTime * (location.getTime() - this.lastLocation.getTime()) / deltaLocationTime;
						location.setLatitude((float)(this.lastLocation.getLatitude() + deltaLatitude));
						location.setLongitude((float)(this.lastLocation.getLongitude() + deltaLongitude));
						location.setSpeed(0);
						System.out.println(location + " " + deltaLatitude + " " + deltaLongitude + " " + deltaGpsTime + " " + deltaLocationTime + " " + (gpsLocation.getLatitude() - this.previousGpsLocation.getLatitude()));
					}else{
						location.setLatitude((float)(this.lastLocation.getLatitude()));
						location.setLongitude((float)(this.lastLocation.getLongitude()));
						System.out.println("* " + location);
						location.setSpeed(0);
					}
					this.lastLocation = new Location(location);
				}
			}else{
				this.lastLocation = null;
			}
		}
	}
}
