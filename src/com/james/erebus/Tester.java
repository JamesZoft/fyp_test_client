package com.james.erebus;

public class Tester {
	
	public static void main(String[] args)
	{
		ClientProxy cp = new ClientProxy("http://localhost:3000/");
		cp.postTourny("1/11/12", "tname", "someSponsor", "aLocation", "prizes$$", "someReqs", "someFormat",
				"someStatus!", "linksgalore!");
		cp.getTourny("1", FormatType.json);
	}

}
