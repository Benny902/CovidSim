package Virus;
import Population.Person;
import Population.Sick;

public interface IVirus {
	
	double contagionProbability(Person p);
	boolean tryToContagion(Person p1, Person p2);
	boolean tryToKill(Sick p);
	
}
