package com.zw.config.dozer;

import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerEventListener;
import org.dozer.event.DozerEvent;

@Slf4j
public class CustDozerEventListener implements DozerEventListener {
    @Override
    public void mappingStarted(DozerEvent dozerEvent) {
        log.info("mappingStarted");
    }

    @Override
    public void preWritingDestinationValue(DozerEvent dozerEvent) {
        log.info("preWritingDestinationValue");

    }

    @Override
    public void postWritingDestinationValue(DozerEvent dozerEvent) {
        log.info("postWritingDestinationValue");
    }

    @Override
    public void mappingFinished(DozerEvent dozerEvent) {
        log.info("mappingFinished");
    }
}
