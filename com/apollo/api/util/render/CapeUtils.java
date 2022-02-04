//Decomped by XeonLyfe

package com.apollo.api.util.render;

import java.util.*;
import java.net.*;
import java.io.*;

public class CapeUtils
{
    List<UUID> uuids;
    
    public CapeUtils() {
        this.uuids = new ArrayList<UUID>();
        try {
            final URL pastebin = new URL("");
            final BufferedReader in = new BufferedReader(new InputStreamReader(pastebin.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                this.uuids.add(UUID.fromString(inputLine));
            }
        }
        catch (Exception ex) {}
    }
    
    public boolean hasCape(final UUID id) {
        return this.uuids.contains(id);
    }
}
