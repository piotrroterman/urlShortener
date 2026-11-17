package com.piotr.url_shortener;

import java.util.List;
import java.util.UUID;

public interface LinkService {

    public Link createLink(Link link);

    public List<Link> getAllLink();

    public Link updateLink(UUID id, Link link);

    public void deleteLink(UUID shortenedLink);
}
