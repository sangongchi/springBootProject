package org.sangongchi.projectbyspringboot.model;

import lombok.Data;

/**
 * @author yangpei
 * @date 2026/3/18
 */
@Data
public class DifyWorkflowReBody {

		private String text;
		private Img img;

		@Data
		public static class Img {
			private String type;
			private String transfer_method;
			private String url;
			private String upload_file_id;
		}
}
