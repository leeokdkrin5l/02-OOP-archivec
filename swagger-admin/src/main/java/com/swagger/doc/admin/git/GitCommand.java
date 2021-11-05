package com.swagger.doc.admin.git;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.*;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * @author kangwang
 * @Description:
 * @date 2020/9/1
 */
public class GitCommand {
    public static void cloneCode(String gitAddr, String branch, File fileDirectory) throws GitAPIException {
        if (StringUtils.isEmpty(branch)) {
            branch = "master";
        }
        CloneCommand cloneCommand = Git.cloneRepository();
        cloneCommand.setURI(gitAddr);
        cloneCommand.setBranch(branch);
        cloneCommand.setDirectory(fileDirectory);
        UsernamePasswordCredentialsProvider user = new UsernamePasswordCredentialsProvider("kennenwang", "Wk@lhy1314..");
        cloneCommand.setCredentialsProvider(user);
        cloneCommand.call().checkout();
    }

    public static void main(String[] args) throws GitAPIException {
        cloneCode("http://git.code.oa.com/tacs/SceneOpDataTransfer.git", "", null);
    }
}
