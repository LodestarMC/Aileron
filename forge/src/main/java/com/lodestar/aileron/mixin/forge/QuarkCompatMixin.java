package com.lodestar.aileron.mixin.forge;

import net.minecraftforge.forgespi.language.ModFileScanData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Pseudo
@Mixin(targets = "vazkii.quark.base.module.ModuleFinder")
public class QuarkCompatMixin {

    @ModifyVariable(method = "loadModule(Lnet/minecraftforge/forgespi/language/ModFileScanData$AnnotationData;)V", at = @At(value = "STORE"), remap = false)
    private Map<String, Object> annotationData(Map<String, Object> originalVals, ModFileScanData.AnnotationData target) {
        String clazzname = target.memberName();
        HashMap<String, Object> vals = new HashMap<>(originalVals);

        if(clazzname.contains("BetterElytraRocketModule") || clazzname.contains("CampfiresBoostElytraModule") ) {
            List<String> antiOverlap = vals.containsKey("antiOverlap") ? new ArrayList<>((List<String>) vals.get("antiOverlap")) : new ArrayList<>();
            antiOverlap.add("aileron");

            // just to be safe
            vals.put("antiOverlap", antiOverlap);
        }

        return vals;
    }
}
