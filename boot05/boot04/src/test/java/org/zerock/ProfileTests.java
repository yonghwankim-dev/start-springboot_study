package org.zerock;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.domain.Member;
import org.zerock.domain.Profile;
import org.zerock.persistence.MemberRepository;
import org.zerock.persistence.ProfileRepository;

import lombok.extern.java.Log;

@SpringBootTest
@Log
@Commit	// 테스트 결과 commit
class ProfileTests {
	
	@Autowired
	MemberRepository memberRepo;

	@Autowired
	ProfileRepository profileRepo;

	// 100명의 사용자 생성
	@Disabled
	@Test
	public void testInsertMembers() {
		IntStream.range(1, 101).forEach(i->{
			Member member = new Member();
			member.setUid("user"+i);
			member.setUpw("pw"+i);
			member.setUname("사용자"+i);
			
			memberRepo.save(member);
		});
	}
	
	// user1이 사용하는 프로필 사진 5장 생성
	@Disabled
	@Test
	public void testInsertProfile() {
		Member member = new Member();
		member.setUid("user1");
		
		for(int i=1;i<5;i++) 
		{
			Profile profile1 = new Profile();
			profile1.setFname("face"+i+".jpg");
			
			if(i==1) 
			{
				profile1.setCurrent(true);
			}
			profile1.setMember(member);
			profileRepo.save(profile1);
			
		}
	}

	// user1의 회원ID와 user1이 사용하는 프로필 사진 개수 출력 
	@Disabled
	@Test
	public void testFetchJoint1() {
		List<Object[]> result = memberRepo.getMemberWithProfileCount("user1");
		
		result.forEach(arr -> System.out.println(Arrays.toString(arr)));
	}

	// user1의 정보 및 현재 사용하는 프로필 사진 정보 출력 
//	@Disabled
	@Test
	public void testFetchJoint2() {
		List<Object[]> result = memberRepo.getMemberWithProfile("user1");
		
		result.forEach(arr -> System.out.println(Arrays.toString(arr)));
	}
	

}
