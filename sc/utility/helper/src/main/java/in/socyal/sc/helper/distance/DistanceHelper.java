package in.socyal.sc.helper.distance;

import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

@Component
public class DistanceHelper {
	private static ResourceBundle resource = ResourceBundle.getBundle("bananaa-application");
	private static final String CHECKIN_RANGE = "checkin.range";
	
	public static Boolean isCoordinateInRange(double lat1, double lon1, double lat2, double lon2) {
		Boolean flag = Boolean.FALSE;
		Integer checkinRange = Integer.valueOf(resource.getString(CHECKIN_RANGE));
		double distance = distance(lat1, lon1, lat2, lon2, DistanceUnitType.KM.getCode());
		if (distance * 1000 < checkinRange) {
			flag = Boolean.TRUE;
		}
		//FIXME
		return Boolean.TRUE;
	}
	
	public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "M") {
			dist = dist * 0.8684;
		}

		//Rounds off to 2 decimals
		return (Math.round(dist * 100.0) / 100.0);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
}
