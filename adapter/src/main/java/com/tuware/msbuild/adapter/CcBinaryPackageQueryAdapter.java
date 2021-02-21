package com.tuware.msbuild.adapter;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.BazelQueryMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CcBinaryPackageQueryAdapter implements BazelQueryMapper<Build.QueryResult> {

    @Override
    public List<String> getCppSourceFiles(Build.QueryResult queryResult) {
        List<String> sourceFileList = new ArrayList<>();

        queryResult.getTargetList().stream()
                .filter(target -> target.getType().equals(Build.Target.Discriminator.RULE))
                .map(Build.Target::getRule)
                .filter(rule -> rule.getRuleClass().equals("cc_binary"))
                .forEach(
                        rule -> rule.getRuleInputList().stream()
                                .filter(ruleInput -> !ruleInput.contains("@"))
                                .forEach(sourceFileList::add)
                );

        return sourceFileList;
    }

}
