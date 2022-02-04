//Decomped by XeonLyfe

package org.spongepowered.asm.service;

public interface IMixinServiceBootstrap
{
    String getName();
    
    String getServiceClassName();
    
    void bootstrap();
}
