package tld.unknown.mystery.util;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public final class FluidHelper {

    public static boolean isTankFull(IFluidHandler handler) {
        return handler.getFluidInTank(0).getAmount() >= handler.getTankCapacity(0);
    }

    public static boolean isTankEmpty(IFluidHandler handler) {
        return handler.getFluidInTank(0).isEmpty();
    }

    public static String serializeTankStatus(IFluidHandler tank) {
        return String.format("%d/%d [%s]", tank.getFluidInTank(0).getAmount(), tank.getTankCapacity(0), tank.getFluidInTank(0).getFluid().getFluidType().getDescription().getString());
    }

    public static String serializeFluidStack(FluidStack stack) {
        return String.format("%dx %s", stack.getAmount(), stack.getFluid().getFluidType().getDescription().getString());
    }
}
