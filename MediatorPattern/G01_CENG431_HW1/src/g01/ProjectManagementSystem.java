package g01;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.google.gson.*;
public class ProjectManagementSystem {
	private ArrayList<Project> projects;
	private Menu menu;
	private FileOperations fileOp;
	private ArrayList<Resource> resources;
	
	public ProjectManagementSystem() {
		setMenu();
		setProjects();
		setFileOperations();
		setResources();
	}
	
	
	
	public void processInput(int number) {
		
		// If the input is 1, user wants to add new PROJECT 
		if (number == 1) {
			addProject();
		}
		// If number is 2, then user wants to remove an project
		else if(number == 2){
			removeProject();
		}

		else if(number == 4) {}
		
		else if(number == 5) {
			//YOK fonksiyonu "5.Find number of distinct employees and consultants assinged to a project,activity and task")
		}
		
		//If number is 6, the user wants to add an activity
		else if(number == 6) {
			addActivity();
		}
		//If number is 7, the user wants to remove an activity
		else if(number == 7) {
			removeActivity();	
		}
		//If number is 8, the user wants to update an activity
		else if(number == 8) {
			updateActivity();
		}
		//If number is 9, the user wants to add an task
		else if(number == 9) {
			addTask();
		}
		//If number is 10, the user wants to remove an task
		else if(number == 10) {
			removeTask();
		}
		//If number is 11, the user wants to update an task
		else if(number == 11) {
			updateTask();
		}
		//If number is 12, the user wants to assign a resorce to an task
		else if(number == 12) {
			assignResourceToTask();
		}
		//If number is 13, the user wants to unassign resorce from an task
		else if(number == 13) {
			unAssignResourceToTask();
		}
		//If number is 14, the user wants to calculate the duration of a project
		else if(number == 14) {
			calcDurationOfProject();
		}
		//If number is 15, the user wants to calculate the duration of an activity
		else if(number == 15) {
			calcDurationOfActivity();
		}
	}
	public ArrayList<Project> getProjects() {
		return projects;
	}
	public void setProjects() {
		this.projects = new ArrayList<Project>();
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu() {
		this.menu = new Menu();
	}
	public void setFileOperations() {
		this.fileOp = new FileOperations();
	}
	public void setResources() {
		this.resources = new ArrayList<Resource>();
	}
	public ArrayList<Resource> getResources() {
		return resources;
	}





	// Project functions
	public void addProject() {	
		this.menu.printToConsole("Please enter the name of the project: ");
		String name = this.menu.getStringInput();
		
		this.menu.printToConsole("Please enter the description of the project: ");
		String desc = this.menu.getStringInput();
		
		this.menu.printToConsole("Please enter the start date of the project: (example date format YYYY-MM-DD ) ");
		String stringDate = this.menu.getStringInput();
		Date date = this.formatDate(stringDate);
		
		Project project = new Project(name,desc,date);

		this.projects.add(project);
		
		fileOp.writeToFile(this);
		
		
		menu.printToConsole("The new project has been added.\n");
	}
	public Project findProject(String name) {
		for(Project pr : this.projects) {
			if(pr.getName().equals(name)) { 
				return pr;
				}
		}
		return null;
	}
	public void removeProject() {
		this.printProjects();
		menu.printToConsole("Please write the name of the project that you want to remove:");
		String name = menu.getStringInput();
		Project p = this.findProject(name);
		// Checking if the project exists or not
		if(!isNull(p)) {
			this.projects.remove(p);
			menu.printToConsole("Project has been removed.\n");
		}
		else {
			menu.printToConsole("The project is not found!\n");
		}
	}
	public void printProjects() {
		menu.printToConsole("****************************************************PROJECTS****************************************************\n ");
		for(Project p: this.projects) {
			menu.printToConsole(  " Name: " + p.getName() + ", Project Desc: " + p.getDesc() +", StartDate: " +p.getStartDate()+ "\n");
		}
		menu.printToConsole("******************************************************************************************************************\n ");
	}
	
	public boolean isNull(Object p) {
		if(p == null) {
			return true;
		}
		return false;
	}
	public Date formatDate(String sDate) {
		 try {
			 Date date=new SimpleDateFormat("YYYY-MM-DD").parse(sDate); 
			 return date;
		 }
		 catch(ParseException e){
			 e.printStackTrace(); 
			 menu.printToConsole("Please give another valid date");
			 return null;
		 }
	}
	
	// Activity functions
	public void printActivities(Project p) {	
		menu.printToConsole("******************************************** ACTIVITIES ********************************************\n ");
		if(p.getActivities().size() == 0) {
			menu.printToConsole("There is no activities to show!\n");
		}
		for(int i= 0; i< p.getActivities().size(); i++) {
			Activity act = p.getActivities().get(i);
			menu.printToConsole("Number : "+act.getNumber() + ",ActDesc : " +act.getDesc() + ", StartDate: "	+act.getStartDate() + "\n");
		}
		menu.printToConsole("****************************************************************************************************\n ");
	}
	public void addActivity() 	{
		printProjects();
		menu.printToConsole("Please enter the name of the project that you want to add acvitiy in it: ");
		String name = menu.getStringInput();
		Project p = this.findProject(name);
		// Checking if the project exists or not
		if(!isNull(p)) {
			menu.printToConsole("Please enter the number of activity : ");
			int num = menu.getIntInput();

			menu.printToConsole("Please enter the description of activity ");
			String desc = menu.getStringInput();
			
			this.menu.printToConsole("Please enter the start date of the project: (example date format YYYY-MM-DD ) ");
			String sDate = this.menu.getStringInput();
			Date date = this.formatDate(sDate);
			p.addActivity(num, desc, date);
			fileOp.writeToFile(this);
			menu.printToConsole("The activity has been added!\n");
		}
	}
	public void updateActivity() {
		printProjects();
		menu.printToConsole("Please enter the name of the project that you want to update acvitiy from it: ");
		String name = menu.getStringInput();
		Project p = this.findProject(name);
		printActivities(p);
		// Checking if the project exists or not
		if(!isNull(p)) {
			menu.printToConsole("Please enter the number of activity that you want to update: ");
			 int numAct = menu.getIntInput();
			 Activity actToBeUpdated = p.findActivity(numAct);
			// Checking if the activity exists or not
			 if(!isNull(actToBeUpdated)) {
				int chosen = 0;
				while(chosen != 1 && chosen != 2 && chosen !=3 ) {
						menu.printUpdateActivityMenu();
						chosen = menu.getIntInput();
						
						if(chosen == 1) {
							menu.printToConsole("Please enter the new value of the activity number  : ");
							int newNum  = menu.getIntInput();
							//p.getActivities().get(p.findActivityIndex(numAct)).updateNum(newNum);
							actToBeUpdated.updateNum(newNum);
							fileOp.writeToFile(this);
							menu.printToConsole("The number of activity has been Updated!");
						}
						else if(chosen == 2) {
							menu.printToConsole("Please enter the new value of the  activity description : ");
							String newDesc = menu.getStringInput();
							//p.getActivities().get(p.findActivityIndex(numAct)).updateDesc(newDesc);
							actToBeUpdated.updateDesc(newDesc);
							fileOp.writeToFile(this);
							menu.printToConsole("The description of activity has been Updated!");
						}
						else if(chosen == 3) {
							
							this.menu.printToConsole("Please enter the new value of the activity start date : (example date format YYYY-MM-DD ) ");
							String sDate = this.menu.getStringInput();
							Date date = this.formatDate(sDate);
							//p.getActivities().get(p.findActivityIndex(numAct)).updateDate(date);
							actToBeUpdated.updateDate(date);
							fileOp.writeToFile(this);
							menu.printToConsole("The date of activity has been Updated!\n");
						}
						else {
							menu.printToConsole("Invalid Value!\n");
						}
					}
			 } else {
				 menu.printToConsole("There isn't activity with that number\n");
			 }
		}else {
			menu.printToConsole("There isn't project with that name\n");
		}
	}
	public void removeActivity() {
		printProjects();
		menu.printToConsole("Please enter the name of the project that you want to remove acvitiy from: ");
		String name = menu.getStringInput();
		Project p = this.findProject(name);
		// Checking if the project exists or not
		if(!isNull(p)) {
			printActivities(p);
			menu.printToConsole("Please enter the number of the activity that you want to remove activity from it: ");
			int numAct = menu.getIntInput();
			Activity actToBeRemoved = p.findActivity(numAct);
			// Checking if the activity exists or not
			if(!isNull(actToBeRemoved)) {
				p.removeActivity(actToBeRemoved);
				fileOp.writeToFile(this);
				menu.printToConsole("Activity has been removed\n");
			} 
			else {
				 menu.printToConsole("There isn't activity with that number\n");
			 }
		}
		else {
			menu.printToConsole("There isn't project with that name\n");
		}
	}
	
	// Task functions
	public void printTasks(Activity act) {
		menu.printToConsole("******************************************************** TASKS ********************************************************\n ");
		if(act.getTasks().size() == 0) {
			menu.printToConsole("There is no tasks to show!\n");
		}
		for(int i= 0; i< act.getTasks().size(); i++) {
			Task t = act.getTasks().get(i);
			menu.printToConsole("Number: "+t.getNumber() + ", TaskDesc: " +t.getDesc() + ", StartDate: "+t.getStartDate() + ", Hours : " + t.getHours()+
												", ResourceId: " + t.getResource().getResourceId() + "\n"
												);
		}
		menu.printToConsole("************************************************************************************************************************\n ");
	}
	public void addTask() {
		printProjects();
		menu.printToConsole("Please enter the name of the project that you want to add acvitiy in it: ");
		String name = menu.getStringInput();
		Project p = this.findProject(name);
		// Checking if the project exists or not
		if(!isNull(p)) {
			printActivities(p);
			menu.printToConsole("Please enter the number of the activity that you want to add task in it: ");
			int numAct = menu.getIntInput();
			Activity act = p.findActivity(numAct);
			
			// Checking if the activity exists or not
			if(!isNull(act)) {
				menu.printToConsole("Please enter the number of task : ");
				int numTask = menu.getIntInput();

				menu.printToConsole("Please enter the description of task ");
				String desc = menu.getStringInput();
				
				this.menu.printToConsole("Please enter the start date of the task: (example date format YYYY-MM-DD ) ");
				String sDate = this.menu.getStringInput();
				Date date = this.formatDate(sDate);
				
				menu.printToConsole("Please enter the hours of task ");
				int hours = menu.getIntInput();
				
				act.addTask(numTask,desc,date,hours);
				fileOp.writeToFile(this);
				menu.printToConsole("The task has been added!\n");
			}else {
				 menu.printToConsole("There isn't activity with that number\n");
			 }
		}
		else {
			menu.printToConsole("There isn't project with that name\n");
		}
	}
	public void updateTask() {
		printProjects();
		menu.printToConsole("Please enter the name of the project that you want to update task on it: ");
		String name = menu.getStringInput();
		Project p = this.findProject(name);
		// Checking if the project exists or not
		if(!isNull(p)) {
			printActivities(p);
			menu.printToConsole("Please enter the number of the activity that you want to update task on it: ");
			int numAct = menu.getIntInput();
			Activity act = p.findActivity(numAct);
			printTasks(act);
			// Checking if the activity exists or not
			if(!isNull(act)) {
				menu.printToConsole("Please enter the number of task that you want to update: ");
				int numTask = menu.getIntInput();
				Task taskToBeUpdated = act.findTask(numTask);
				// Checking if the task exists or not
				if(!isNull(taskToBeUpdated)) {
					int chosen = 0;
					while(chosen != 1 && chosen != 2 && chosen !=3 && chosen != 4) {
							menu.printUpdateTaskMenu();
							chosen = menu.getIntInput();
							
							if(chosen == 1) {
								menu.printToConsole("Please enter the new value of the task number  : ");
								int newNum  = menu.getIntInput();
								
								taskToBeUpdated.updateNum(newNum);
								fileOp.writeToFile(this);
								menu.printToConsole("The number of activity has been Updated!\n");
							}
							else if(chosen == 2) {
								menu.printToConsole("Please enter the new value of the  task description : ");
								String newDesc = menu.getStringInput();
								
								taskToBeUpdated.updateDesc(newDesc);
								fileOp.writeToFile(this);
								menu.printToConsole("The description of activity has been Updated!\n");
							}
							else if(chosen == 3) {
								
								this.menu.printToConsole("Please enter the new value of the task start date : (example date format YYYY-MM-DD ) ");
								String sDate = this.menu.getStringInput();
								Date date = this.formatDate(sDate);
								
								taskToBeUpdated.updateDate(date);
								fileOp.writeToFile(this);
								menu.printToConsole("The date of activity has been Updated!\n");
							}
							else if(chosen == 4) {
								menu.printToConsole("Please enter the new hours of the task number  : ");
								int newHours  = menu.getIntInput();
							
								taskToBeUpdated.updateNum(newHours);
								fileOp.writeToFile(this);
								menu.printToConsole("The hour of activity has been Updated!\n");
							}
							else {
								menu.printToConsole("Invalid Value!\n");
							}
						}
				}else {
					menu.printToConsole("There isn't task with that number \n");
				}
			}else {
				 menu.printToConsole("There isn't activity with that number\n");
			 }
		}else {
			menu.printToConsole("There isn't project with that name\n");
		}		
	}
	public void removeTask() {
		printProjects();
		menu.printToConsole("Please enter the name of the project that you want to remove a task from: ");
		String name = menu.getStringInput();
		Project p = this.findProject(name);
		// Checking if the project exists or not
		if(!isNull(p)) {
			printActivities(p);
			menu.printToConsole("Please enter the number of the activity that you want to remove a task from :");
			int numAct = menu.getIntInput();
			Activity act = p.findActivity(numAct);
			// Checking if the activity exists or not
			if(!isNull(act)) {
				printTasks(act);
				menu.printToConsole("Please enter the number of task that you want to remove: ");
				int numTask = menu.getIntInput();
				Task taskToBeRemoved = act.findTask(numTask);
				// Checking if the task exists or not
				if(!isNull(taskToBeRemoved)) {
					act.removeTask(taskToBeRemoved);
					fileOp.writeToFile(this);
					menu.printToConsole("Task has been removed.\n");
				}else {
					menu.printToConsole("There isn't task with that number \n");
				}
			}else {
				 menu.printToConsole("There isn't activity with that number\n");
			 }
		}else {
			menu.printToConsole("There isn't project with that name\n");
		}
	}
	
	// Resource functions
	public void addResource() {
	}
	public void updateResource() 
	{
		menu.printToConsole("hacý proje adý ne");
	}
	public void removeResource() 
	{
		menu.printToConsole("hacý proje adý ne");
	}
	
	public int evenOrOddIDGenerate(int type) {
		if (type ==1 ) { // resource type is employee, so it should have odd id
			int id = new Random().nextInt(1000); 
			if(id % 2 == 0) {
				id = id + 1;
			}	
			return id;
		}
		else if(type == 2) { // resource type is consultant, so it should have even id
			int id = new Random().nextInt(1000); 
			if(id % 2 != 0) {
				id = id * 2;
			}
			return id;
		}
		return -1;	
	}
	public int generateResourceId(int type) {
		boolean flag = true;
		int id = evenOrOddIDGenerate(type);
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for(int i=0; i<this.resources.size();i++){
			ids.add(this.resources.get(i).getResourceId());
		}
		while (ids.contains(id)) {
			id = evenOrOddIDGenerate(type);
		}
		return id;
	}
	
	public void assignResourceToTask() {
		printProjects();
		menu.printToConsole("Please enter the name of the project that you want to assign resource the task of it: ");
		String name = menu.getStringInput();
		Project p = this.findProject(name);
		// Checking if the project exists or not
		if(!isNull(p)) {
			printActivities(p);
			menu.printToConsole("Please enter the number of the activity that you want to assign resource the task of it:  ");
			int numAct = menu.getIntInput();
			Activity act = p.findActivity(numAct);
			// Checking if the activity exists or not
			if(!isNull(act)) {
				printTasks(act);
				menu.printToConsole("Please enter the number of task that you want to assign resource:  ");
				int numTask = menu.getIntInput();
				Task task = act.findTask(numTask);
				// Checking if the task exists or not
				if(!isNull(task)) {
					//Checking if there are resources which already exists
					if(resources.size() == 0 ) { // there is no resource yet
						menu.printToConsole("Please choose 1 or 2 for the resource type that you want to assign: (1 for EMPLOYEE 2 for CONSULTANT");
						int type = menu.getIntInput();
						menu.printToConsole("Please enter the name of the person: ");
						String pName = menu.getStringInput();
						if(type == 1) {
							//creatig the new resource 
							int id = generateResourceId(type);
							Resource emp = new Employee(pName,id);
							resources.add(emp);
							task.assignResource(emp);
							menu.printToConsole("The resource with " + emp.getResourceId() +"  has been assigned!\n");
						}
						else if(type == 2) {
							//creatig the new resource 
							int id = generateResourceId(type);
							Resource consultant = new Consultant(pName,id);
							resources.add(consultant);
							task.assignResource(consultant);
							menu.printToConsole("The resource with " + consultant.getResourceId() +"  has been assigned!\n");
						}
					}
					else { // there are some resources
						// printing the resources have been already added to other tasks
						for(Resource r: resources) {
							menu.printToConsole("Resource ID: " + r.getResourceId()+" Name: " + ((Person) r).getName());
						}
						menu.printToConsole("To add one of these resources press 1 or to add another new resource press 2.");
						int newOrExisted = menu.getIntInput();
						//checking if the user wants to assign existed resource
						if(newOrExisted == 1) { // existed resource
							menu.printToConsole("Choose an Id");
							int resourceId = menu.getIntInput();
							menu.printToConsole("The resource with " + resourceId +"  has been assigned!\n");
						}
						else if(newOrExisted == 2) { // new resource
							menu.printToConsole("Please choose 1 or 2 for the resource type that you want to assign: (1 for EMPLOYEE 2 for CONSULTANT");
							int type = menu.getIntInput();
							menu.printToConsole("Please enter the name of the person: ");
							String pName = menu.getStringInput();
							if(type == 1) {
								int id = generateResourceId(type);
								Resource emp = new Employee(pName,id);
								resources.add(emp);
								task.assignResource(emp);
								menu.printToConsole("The resource with " + emp.getResourceId() +"  has been assigned!\n");
							}
							else if(type == 2) {
								int id = generateResourceId(type);
								Resource consultant = new Consultant(pName,type);
								resources.add(consultant);
								task.assignResource(consultant);
								menu.printToConsole("The resource with " + consultant.getResourceId() +"  has been assigned!\n");
							}
						}
					}
					fileOp.writeToFile(this);
				}else {
					menu.printToConsole("There isn't task with that number \n");
				}
			}else {
				 menu.printToConsole("There isn't activity with that number\n");
			 }
		}else {
			menu.printToConsole("There isn't project with that name\n");
		}
	}
	public void unAssignResourceToTask() {
		printProjects();
		menu.printToConsole("Please enter the name of the project that you want to unassign resource the task of it: ");
		String name = menu.getStringInput();
		Project p = this.findProject(name);
		printActivities(p);
		// Checking if the project exists or not
		if(!isNull(p)) {
			printActivities(p);
			menu.printToConsole("Please enter the number of the activity that you want to unassign resource the task of it:  ");
			int numAct = menu.getIntInput();
			Activity act = p.findActivity(numAct);
			// Checking if the activity exists or not
			if(!isNull(act)) {
				printTasks(act);
				menu.printToConsole("Please enter the number of task that you want to unassign resource:  ");
				int numTask = menu.getIntInput();
				Task task = act.findTask(numTask);
				// Checking if the task exists or not
				if(!isNull(task)) {
					task.unAssignResource();
					fileOp.writeToFile(this);
					menu.printToConsole("The resource"
							+ " has been unassigned!\n");
				}else {
					menu.printToConsole("There isn't task with that number \n");
				}
			}else {
				 menu.printToConsole("There isn't activity with that number\n");
			 }
		}else {
			menu.printToConsole("There isn't project with that name\n");
		}
	}
	
	
	
	
	
	
	public void calcDurationOfProject(){
		printProjects();
		menu.printToConsole("Please enter the name of the project that you want to calculate the duration of it \n");
		String projectName = menu.getStringInput();
		Project p = this.findProject(projectName);
		// Checking if the project exists or not
		if(!isNull(p)) {
			int duration = p.calcProjectDuration();
			menu.printToConsole("Name: " + p.getName() + ", Duration: " + duration + "\n");
		}
		else {
			menu.printToConsole("There isn't project with that name\n");
		}
		
	}
	public void calcDurationOfActivity() {
		printProjects();
		menu.printToConsole("Please enter the name of the project \n");
		String projectName = menu.getStringInput();
		Project p = this.findProject(projectName);
		// Checking if the project exists or not
		if(!isNull(p)) {
			printActivities(p);
			menu.printToConsole("Please enter the number of the actvitiy that you want to calculate the duration of it \n");
			int numAct = menu.getIntInput();
			Activity act = p.findActivity(numAct);
			// Checking if the activity exists or not
			if(!isNull(act)) {
				int duration = act.calcActivityDuration();
				menu.printToConsole("Name: " + p.getName() + ", Duration: " + duration + "\n");
			}else {
				 menu.printToConsole("There isn't activity with that number\n");
			 }
		}else {
			menu.printToConsole("There isn't project with that name\n");
		}
	}
	
	public void findNoResources() {	
	}
	public void printDeliverables() {
		if (projects.size() != 0) {
			for(Project p: projects) {
				if(!p.isDeliverable()) {
					menu.printToConsole("Project name:  "+ p.getName() + " is not ready to deliver!");
				}
				else {
					menu.printToConsole("Project name: "+ p.getName() + " is ready to deliver!");	
				}
			}
		}
		else {
			menu.printToConsole("There isn't a project to deliver.\n");
		}
	}
	public void start() {
		fileOp.readFile(this);
		System.out.println(this.getProjects().size());
		boolean temp=true;
		int input;
		
		while(temp) {
			//dosyayý okuyuo objeleri yaratacak o objeleri ilgii proje arrayine atacak
			menu.createMenu();
			input = menu.getIntInput();
			
			//checking if the input is valid or not for main menu 
			if(input > 0 && input < 6) {
				// if the input is 3, user should see the subMenu 
				if(input == 3) {
					menu.createSubMenu();
					input = menu.getIntInput();
					
					//checking if the input is valid or not for subMenu 
					if(input > 5 && input<14) {
						this.processInput(input);
					}
					else {
						menu.printToConsole("Invalid input.");
					}
				}
				// if the input is different than 3, then input can be processed
				else if(input == 4) {
					menu.createCalculateMenu();
					input = menu.getIntInput();
					this.processInput(input);
				}
				else {
					this.processInput(input);
				}
			}
			// if the input is -1, then program ends
			else if(input == -1) {
				temp = false;
				printDeliverables();
			}
			// if input is different than 1-6 or -1 then it is not valid.
			else {
				menu.printToConsole("Invalid Input");
			}		
		}	
	}	
}
