package com.trackerimmo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.DataType;
import io.milvus.param.ConnectParam;
import io.milvus.param.R;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.FieldType;
import io.milvus.param.collection.HasCollectionParam;

@Configuration
public class MilvusConfig {

	@Autowired
	MilvusProperties milvusProperties;

	@Bean
	public MilvusServiceClient milvusClient() {
		return new MilvusServiceClient(ConnectParam.newBuilder().withHost(milvusProperties.getHost())
				.withPort(milvusProperties.getPort()).build());
	}

	@Bean
	public CreateCollectionParam createCollectionReq() {
		FieldType fieldType1 = FieldType.newBuilder().withName("book_id").withDataType(DataType.Int64)
				.withPrimaryKey(true).withAutoID(false).build();
		FieldType fieldType2 = FieldType.newBuilder().withName("word_count").withDataType(DataType.Int64).build();
		FieldType fieldType3 = FieldType.newBuilder().withName("book_intro").withDataType(DataType.FloatVector)
				.withDimension(2).build();

		return CreateCollectionParam.newBuilder().withCollectionName("book").withDescription("Test book search")
				.withShardsNum(2).addFieldType(fieldType1).addFieldType(fieldType2).addFieldType(fieldType3).build();

	}

	@Bean
	public void createCollection() {
		R<Boolean> respHasCollection = milvusClient()
				.hasCollection(HasCollectionParam.newBuilder().withCollectionName("book").build());
		if (respHasCollection.getData() == Boolean.FALSE) {
			milvusClient().createCollection(createCollectionReq());
			System.out.println("Collection book is created.");
		}
		
	}

}
