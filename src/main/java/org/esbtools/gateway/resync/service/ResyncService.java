package org.esbtools.gateway.resync.service;

import org.esbtools.gateway.resync.ResyncRequest;
import org.esbtools.gateway.resync.ResyncResponse;
import org.springframework.stereotype.Service;

@Service
public interface ResyncService {

    ResyncResponse resync(ResyncRequest resyncRequest);

    String getSystemName();
}
