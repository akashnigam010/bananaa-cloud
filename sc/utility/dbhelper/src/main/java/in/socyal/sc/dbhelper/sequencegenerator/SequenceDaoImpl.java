package in.socyal.sc.dbhelper.sequencegenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class SequenceDaoImpl implements SequenceDao {
	@Autowired MongoTemplate senseMongoTemplate;
	
	private static final String _ID = "_id";
	private static final String SEQ = "seq";

	public void setSenseMongoTemplate(MongoTemplate senseMongoTemplate) {
		this.senseMongoTemplate = senseMongoTemplate;
	}

	@Override
	public int getNextSequenceId(String key) throws SequenceException {
		try {
			//Get sequence id for respective 'key' collection
			Query query = new Query(Criteria.where(_ID).is(key));
			//Increase sequence ID by 1
			Update update = new Update();
			update.inc(SEQ, 1);
			//Return the incremented sequence ID
			FindAndModifyOptions options = new FindAndModifyOptions();
			options.returnNew(true);
			// this is the magic happened
			SequenceId seqId = senseMongoTemplate.findAndModify(query, update, options, SequenceId.class);
			// if no id, throws SequenceException
			// optional, just a way to tell user when the sequence id is failed to generate.
			if (seqId == null) {
				throw new SequenceException("Unable to get sequence id for key : " + key);
			}
			return seqId.getSeq();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SequenceException("Exception occured.. While generating sequence ID for key :" + key);
		}
	}
}