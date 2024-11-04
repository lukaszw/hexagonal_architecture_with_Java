package dev.davivieira.topologyinventory.framework.adapters.output.mysql;

import dev.davivieira.topologyinventory.application.ports.output.SwitchManagementOutputPort;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.mappers.RouterMapper;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.repository.SwitchManagementRepository;

public class SwitchManagementMySQLAdapter implements SwitchManagementOutputPort {

    private final SwitchManagementRepository switchManagementRepository;

    public SwitchManagementMySQLAdapter(SwitchManagementRepository switchManagementRepository) {
        this.switchManagementRepository = switchManagementRepository;
    }

    @Override
    public Switch retrieveSwitch(Id id) {
        var switchData = switchManagementRepository.findById(id.getUuid()).subscribe().asCompletionStage().join();
        return RouterMapper.switchDataToDomain(switchData);
    }
}
