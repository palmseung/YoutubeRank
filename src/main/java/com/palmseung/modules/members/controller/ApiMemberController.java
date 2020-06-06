package com.palmseung.modules.members.controller;

import com.palmseung.infra.jwt.JwtTokenProvider;
import com.palmseung.modules.members.LoginUser;
import com.palmseung.modules.members.MemberConstant;
import com.palmseung.modules.members.domain.Member;
import com.palmseung.modules.members.dto.*;
import com.palmseung.modules.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = MemberConstant.BASE_URI_MEMBER_API)
public class ApiMemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid CreateMemberRequestView request) {
        Member createdMember = memberService.create(request.toEntity());
        CreateMemberResponseView responseView = CreateMemberResponseView.of(createdMember);
        CreateMemberResponseResource resource = applyHateoasToCreate(responseView);

        return ResponseEntity
                .created(URI.create(MemberConstant.BASE_URI_MEMBER_API + "/" + createdMember.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        memberService.delete(memberService.findById(id));

        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestView request) {
        Member member = memberService.login(request.getEmail(), request.getPassword());
        String token = jwtTokenProvider.createToken(member.getEmail());
        LoginResponseResource resource = applyHateoasToLogin(token, member);

        return ResponseEntity
                .created(URI.create("/oauth/token"))
                .body(resource);
    }

    @GetMapping("/my-info/{id}")
    public ResponseEntity retrieveMyInfo(@PathVariable Long id) {
        Member member = memberService.findById(id);
        MyInfoResponseResource resource = applyHateoasToFind(member, id);

        return ResponseEntity
                .ok()
                .body(resource);
    }

    @PutMapping("/my-info/{id}")
    public ResponseEntity updateMyInfo(@LoginUser Member loginUser,
                                       @PathVariable Long id,
                                       @RequestBody UpdateMemberRequestView requestView) {
        Member updatedMember = memberService.updateInfo(loginUser, id, requestView.getNewPassword());
        UpdateMemberResponseResource resource = applyHateoasToUpdate(updatedMember, id);

        return ResponseEntity
                .ok()
                .body(resource);
    }

    private CreateMemberResponseResource applyHateoasToCreate(CreateMemberResponseView responseView) {
        CreateMemberResponseResource resource = new CreateMemberResponseResource(responseView);
        resource.add(new Link("/docs/api-guide.html#resources-members-create")
                .withRel("profile"));
        resource.add(linkTo(ApiMemberController.class)
                .withSelfRel());
        resource.add(linkTo(ApiMemberController.class)
                .slash("login")
                .withRel("login"));
        return resource;
    }

    private LoginResponseResource applyHateoasToLogin(String token, Member member) {
        LoginResponseResource resource = new LoginResponseResource(LoginResponseView.of(token));
        resource.add(new Link("/docs/api-guide.html#resources-members-login").withRel("profile"));
        resource.add(linkTo(ApiMemberController.class)
                .slash("login")
                .withSelfRel());
        resource.add(linkTo(ApiMemberController.class)
                .slash("my-info")
                .slash(member.getId())
                .withRel("retrieve-myInfo"));
        return resource;
    }

    private MyInfoResponseResource applyHateoasToFind(Member member, Long id) {
        MyInfoResponseResource resource = new MyInfoResponseResource(MyInfoResponseView.of(member));
        resource.add(new Link("/docs/api-guide.html#resources-members-myInfo")
                .withRel("profile"));
        resource.add(linkTo(ApiMemberController.class)
                .slash("/my-info")
                .slash(id)
                .withSelfRel());
        resource.add(linkTo(ApiMemberController.class)
                .slash("my-info")
                .slash(id)
                .withRel("update-myInfo"));
        return resource;
    }

    private UpdateMemberResponseResource applyHateoasToUpdate(Member updatedMember, Long id) {
        UpdateMemberResponseResource resource
                = new UpdateMemberResponseResource(UpdateMemberResponseView.of(updatedMember));
        resource.add(linkTo(ApiMemberController.class).slash("my-info").slash(id).withSelfRel());
        resource.add(new Link("/docs/api-guide.html#resources-members-update-myInfo").withRel("profile"));
        return resource;
    }
}
