package org.esbtools.gateway.resync;

import org.springframework.stereotype.Service;

@Service
public interface ResyncService {
    ResyncResponse resync(ResyncRequest resyncRequest);
    String getSystemName();
}
