package com.codephillip.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Api(
        name = "topPlayersApi",
        version = "v1",
        resource = "topPlayers",
        namespace = @ApiNamespace(
                ownerDomain = "backend.codephillip.com",
                ownerName = "backend.codephillip.com",
                packagePath = ""
        )
)
public class TopPlayersEndpoint {

    private static final Logger logger = Logger.getLogger(TopPlayersEndpoint.class.getName());
    private static final int DEFAULT_LIST_LIMIT = 10;

    static {
        ObjectifyService.register(TopPlayers.class);
    }

    @ApiMethod(
            name = "get",
            path = "topPlayers/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public TopPlayers get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting TopPlayers with ID: " + id);
        TopPlayers topPlayers = ofy().load().type(TopPlayers.class).id(id).now();
        if (topPlayers == null) {
            throw new NotFoundException("Could not find TopPlayers with ID: " + id);
        }
        return topPlayers;
    }

    @ApiMethod(
            name = "insert",
            path = "topPlayers",
            httpMethod = ApiMethod.HttpMethod.POST)
    public TopPlayers insert(TopPlayers topPlayers) {
        ofy().save().entity(topPlayers).now();
        logger.info("Created TopPlayers.");

        return ofy().load().entity(topPlayers).now();
    }

    @ApiMethod(
            name = "update",
            path = "topPlayers/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public TopPlayers update(@Named("id") Long id, TopPlayers topPlayers) throws NotFoundException {
        checkExists(id);
        ofy().save().entity(topPlayers).now();
        logger.info("Updated TopPlayers: " + topPlayers);
        return ofy().load().entity(topPlayers).now();
    }

    @ApiMethod(
            name = "remove",
            path = "topPlayers/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(TopPlayers.class).id(id).now();
        logger.info("Deleted TopPlayers with ID: " + id);
    }

    @ApiMethod(
            name = "list",
            path = "topPlayers",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<TopPlayers> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<TopPlayers> query = ofy().load().type(TopPlayers.class).order("-points").limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<TopPlayers> queryIterator = query.iterator();
        List<TopPlayers> topPlayersList = new ArrayList<TopPlayers>(limit);
        while (queryIterator.hasNext()) {
            topPlayersList.add(queryIterator.next());
        }
        return CollectionResponse.<TopPlayers>builder().setItems(topPlayersList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(TopPlayers.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find TopPlayers with ID: " + id);
        }
    }
}