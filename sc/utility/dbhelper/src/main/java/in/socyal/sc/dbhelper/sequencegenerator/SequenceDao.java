package in.socyal.sc.dbhelper.sequencegenerator;

import in.socyal.sc.dbhelper.sequencegenerator.SequenceException;

public interface SequenceDao {
	int getNextSequenceId(String key) throws SequenceException;
}