package niponapo.server.dao;

import niponapo.server.dto.PostInfoDTO;
import niponapo.server.dto.UserPostDTO;

public interface SwapDAO {

	public void swap_request(UserPostDTO input);
	
	public void swap_cancle();
	
	public void swap_accept();
	
	public PostInfoDTO[] swap_list();
	
}
