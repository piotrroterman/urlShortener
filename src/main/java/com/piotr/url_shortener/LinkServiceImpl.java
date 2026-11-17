package com.piotr.url_shortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class LinkServiceImpl implements LinkService {

    private Clock clock;
    private LinksRepository linksRepository;

    public LinkServiceImpl(Clock clock, LinksRepository linksRepository) {
        this.clock = clock;
        this.linksRepository = linksRepository;
    }

    @Override
    public Link createLink(Link link) {
        return linksRepository.save(link);
    }

    public Link findById(UUID shortenedLink) {
        return linksRepository.findById(shortenedLink).orElseThrow();
    }

    @Override
    public List<Link> getAllLink() {
        return linksRepository.findAll();
    }

    @Override
    public Link updateLink(UUID shortenedLink, Link link) {
        return null;
    }

    @Override
    public void deleteLink(UUID shortenedLink) {
        linksRepository.delete(findById(shortenedLink));
    }

    public void deleteOldRecords(Instant recordLiveTime) {
        linksRepository.deleteOldRecords(recordLiveTime);
    }

}
