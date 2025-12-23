package org.sangongchi.projectbyspringboot.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")

public class MongoUser {
	@Id
	private String id;

	private String name;

	private int age;

}
