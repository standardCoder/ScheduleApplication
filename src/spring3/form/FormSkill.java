package spring3.form;

import complexschedulingproblem.Skill;

public class FormSkill {
    
	private String name;
	private Skill skill;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
    public Skill getSkill() {
		return skill;
	}
	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public void createSkill(){
    	skill = new Skill(name); 
    }
}
