package com.example.vstack;

import image.AddImage;
import image.DeleteImage;
import image.ListImages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import compute.DeleteServer;
import compute.ListImages_server;
import compute.ListServers;
import network.AddNetworks;
import network.DeleteNetwork;
import network.ListBeforeUpdateNetworks;
import network.ListNetworks;
import volume.DeleteVol;
import volume.ListBeforeUpdateVol;
import volume.ListVol;
import openstack_connection.updateusers;
import projects.AddProject;
import projects.DeleteProject;
import projects.ListBeforeUpdateProjects;
import projects.ListProject;
import users.AddUser;
import users.DeleteUser;
import users.ListBeforeUpdateUsers;
import users.ListUsers;
import volume.AddVol;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Navigation extends ActionBarActivity implements
		OnChildClickListener {

	private DrawerLayout drawer;
	private ExpandableListView drawerList;
	
	ExpandableListAdapter listAdapter;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	
	Navigation navigation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);

		// preparing list data
		prepareListData();
		initDrawer();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initDrawer() {

		//drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ExpandableListView) findViewById(R.id.lvExp);
		drawerList.setAdapter(new NavigationAdapter(this, listDataHeader,
				listDataChild));

		
		navigation = this;

		drawerList.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(
						getApplicationContext(),
						listDataHeader.get(groupPosition)
								+ " : "
								+ listDataChild.get(
										listDataHeader.get(groupPosition)).get(
										childPosition), Toast.LENGTH_SHORT)
						.show();
				
				switch(groupPosition) {
				case 0:
	                   switch (childPosition) {
	                   case 0: 
	                       Intent addVolIntent= new Intent(Navigation.this,ListImages_server.class);
	                       startActivity(addVolIntent);
	                       navigation.finish();
	                       break;
	                   case 1: 
	                       Intent deleteVolIntent= new Intent(Navigation.this,DeleteServer.class);
	                       startActivity(deleteVolIntent);
	                       navigation.finish();
	                       break;
	                   case 2: 
	                       Intent updateVolIntent= new Intent(Navigation.this,ListServers.class);
	                       startActivity(updateVolIntent);
	                       navigation.finish();
	                       break;
	                   }
	                   break;
				   case 1:
	                   switch (childPosition) {
	                   case 0: 
	                       Intent addVolIntent= new Intent(Navigation.this,AddVol.class);
	                       startActivity(addVolIntent);
	                       navigation.finish();
	                       break;
	                   case 1: 
	                       Intent deleteVolIntent= new Intent(Navigation.this,DeleteVol.class);
	                       startActivity(deleteVolIntent);
	                       navigation.finish();
	                       break;
	                   case 2: 
	                       Intent updateVolIntent= new Intent(Navigation.this,ListBeforeUpdateVol.class);
	                       startActivity(updateVolIntent);
	                       navigation.finish();
	                       break;
	                   case 3: 
	                       Intent listVolIntent= new Intent(Navigation.this,ListVol.class);
	                       startActivity(listVolIntent);
	                       break;
	                   }
	                   break;
                case 2:
                   switch (childPosition) {
                   case 0: 
                       Intent addUserIntent= new Intent(Navigation.this,AddUser.class);
                       startActivity(addUserIntent);
                       navigation.finish();
                       break;
                   case 1: 
                       Intent deleteUserIntent= new Intent(Navigation.this,DeleteUser.class);
                       startActivity(deleteUserIntent);
                       navigation.finish();
                       break;
                   case 2: 
                       Intent updateUserIntent= new Intent(Navigation.this,ListBeforeUpdateUsers.class);
                       startActivity(updateUserIntent);
                       navigation.finish();
                       break;
                   case 3: 
                       Intent listUserIntent= new Intent(Navigation.this,ListUsers.class);
                       startActivity(listUserIntent);
                       break;
                   }
                   break;
               case 3:
                   switch (childPosition) {
                   case 0: 
                       Intent addProjIntent= new Intent(Navigation.this,AddProject.class);
                       startActivity(addProjIntent);
                       navigation.finish();
                   break;
                   case 1: 
                       Intent deleteProjIntent= new Intent(Navigation.this,DeleteProject.class);
                       startActivity(deleteProjIntent);
                       navigation.finish();
                       break;
                   case 2: 
                       Intent updateProjIntent= new Intent(Navigation.this,ListBeforeUpdateProjects.class);
                       startActivity(updateProjIntent);
                       navigation.finish();
                       break;
                   case 3: 
                       Intent listProjIntent= new Intent(Navigation.this,ListProject.class);
                       startActivity(listProjIntent);
                       break;
                   }
                   break;
               case 4:
                   switch (childPosition) {
                   case 0: 
                       Intent addImgIntent= new Intent(Navigation.this,AddImage.class);
                       startActivity(addImgIntent);
                       navigation.finish();
                   break;
                   case 1: 
                       Intent deleteImgIntent= new Intent(Navigation.this,DeleteImage.class);
                       startActivity(deleteImgIntent);
                       navigation.finish();
                       break;
                   case 2: 
                       Intent listImgIntent= new Intent(Navigation.this,ListImages.class);
                       startActivity(listImgIntent);
                       break;
                   }
                   break;
               case 5:
                   switch (childPosition) {
                   case 0: 
                       Intent addProjIntent= new Intent(Navigation.this,AddNetworks.class);
                       startActivity(addProjIntent);
                       navigation.finish();
                   break;
                   case 1: 
                       Intent deleteProjIntent= new Intent(Navigation.this,DeleteNetwork.class);
                       startActivity(deleteProjIntent);
                       navigation.finish();
                       break;
                   case 2: 
                       Intent updateProjIntent= new Intent(Navigation.this,ListBeforeUpdateNetworks.class);
                       startActivity(updateProjIntent);
                       navigation.finish();
                       break;
                   case 3: 
                       Intent listProjIntent= new Intent(Navigation.this,ListNetworks.class);
                       startActivity(listProjIntent);
                       break;
                   }
                   break;
               case 6:
                   switch (childPosition) {
                   case 0: 
                       Intent logout= new Intent(Navigation.this,MainActivity.class);
                       startActivity(logout);
                       navigation.finish();
                   break;
                   case 1: 
                	   finish();
                	   moveTaskToBack(true);
                       System.exit(0);
                       break;
                   }
                   break;
               }
				
				return false;
			}
		});
		
	}

	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("Compute");
		listDataHeader.add("Volume");
		listDataHeader.add("Users");
		listDataHeader.add("Projects");
		listDataHeader.add("Image");
		listDataHeader.add("Network");
		listDataHeader.add("Exit");

		// Adding child data
		List<String> comp = new ArrayList<String>();
		comp.add("Add Instance");
		comp.add("Delete Instance");
		comp.add("List Instance");

		List<String> volume = new ArrayList<String>();
		volume.add("Add volume");
		volume.add("Delete volume");
		volume.add("Update volume");
		volume.add("List volume");

		List<String> users = new ArrayList<String>();
		users.add("Add users");
		users.add("Delete users");
		users.add("Update users");
		users.add("List users");

		List<String> projects = new ArrayList<String>();
		projects.add("Add Projects");
		projects.add("Delete Projects");
		projects.add("Update Projects");
		projects.add("List Projects");
		
		List<String> image = new ArrayList<String>();
		image.add("Add image");
		image.add("Delete image");
		image.add("List image");
		
		List<String> network = new ArrayList<String>();
		network.add("Add network");
		network.add("Delete network");
		network.add("Update network");
		network.add("List network");
		
		List<String> close = new ArrayList<String>();
		close.add("LogOut");
		close.add("Close App");
		

		listDataChild.put(listDataHeader.get(0), comp); // Header, Child data
		listDataChild.put(listDataHeader.get(1), volume);
		listDataChild.put(listDataHeader.get(2), users);
		listDataChild.put(listDataHeader.get(3), projects);
		listDataChild.put(listDataHeader.get(4), image);
		listDataChild.put(listDataHeader.get(5), network);
		listDataChild.put(listDataHeader.get(6), close);
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);

		return false;
	}
}