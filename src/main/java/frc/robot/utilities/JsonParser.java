/**
 * Created by Cameron Coesens in the 2019 Off Season.
 * This allows us to grab a json file from the roborio and read the 2d array
 * to give the information to the motionprofile handler
 */

package frc.robot.utilities;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;

import java.io.FileReader;
import java.util.ArrayList;

public final class JsonParser
{
	/**
	 * Reads a file off of the roborio and reads the values for motion profile and
	 * returns them in a 2d array.
	 * 
	 * @param target
	 *                   the file that contains the points that you want to be read.
	 * @return returns an array for the motion profile.
	 */

	public static ArrayList<BytePoint> RetrieveProfileData(File target)
	{

		ArrayList<BytePoint> points = new ArrayList<BytePoint>();
		String jsonData = readFile(target);
		JSONObject jobj;
		try
		{
			jobj = new JSONObject(jsonData);
			JSONArray jsonArray = new JSONArray(jobj.get("Data").toString());

			for (int i = 0; i < jsonArray.length(); i++)
			{
				try
				{

					JSONObject object1 = jsonArray.getJSONObject(i);

					BytePoint point = new BytePoint();

					point.rotation = object1.getDouble("Rotation");
					point.velocity = object1.getDouble("Velocity");
					point.time = object1.getDouble("Time");
					point.angle = object1.getDouble("Angle");
					point.state = object1.getString("State");

					points.add(point);

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			return points;
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private static String readFile(File fileurl)
	{
		String result = "";
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(fileurl));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null)
			{
				sb.append(line);
				line = br.readLine();
			}
			br.close();
			result = sb.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}
}
