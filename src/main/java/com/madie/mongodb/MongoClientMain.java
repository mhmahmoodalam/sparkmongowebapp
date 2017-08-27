package com.madie.mongodb;

import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

public class MongoClientMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MongoClient mongoClient= new MongoClient("127.0.0.1",27017);
		MongoDatabase db= mongoClient.getDatabase("school");
		MongoCollection<Document> mc= db.getCollection("students");
		System.out.println(mc.count());
		/*mc.drop();
		Document dc= new Document("_id","testid001")
				.append("name","Mahmood Alam")
				.append("age",27)
				.append("dob","24-04-1990")
				;
		mc.insertOne(dc);
		System.out.println(mc.find());*/
		MongoCursor<Document> cursor=mc.find(Filters.eq("scores.type","homework"))
		.sort(Sorts.ascending("name")).iterator();
		//.projection(Projections.include("student_id,score"))
		//.first()
		try{
			String currentStudentId="";
			while(cursor.hasNext()){
				
				Document dc=cursor.next();
				int id=dc.getInteger("_id");
				List<Document> scoresList=(List<Document>)dc.get("scores");
				double lowscore=100;
				//int lowscoreIndex=-1;
				//int index=-1;
				for(Document scoredc:scoresList){
					//index++;
					if(scoredc.getString("type").equals("homework")){
						double score=scoredc.getDouble("score");
						if(score<lowscore){
							lowscore=score;
							//lowscoreIndex=index;
						}
					}
				}
				if(lowscore!=-1){
					Document query= new Document("_id",id);
					Document fields = new Document("scores", 
					        new Document( "score", lowscore));
					Document update = new Document("$pull",fields);
					System.out.println("updating:"+id);
					mc.updateOne(query, update);
				}
				/*if(!currentStudentId.equals(dc.getInteger("student_id").toString())){
					currentStudentId=(String) dc.getInteger("student_id").toString();
					System.out.println("deleteting:"+dc.getInteger("student_id"));
					mc.deleteOne(dc);
				}*/
			}
		}finally{
			cursor.close();
		}
	}

}
