package de.johni0702.minecraft.betterportals.impl.mekanism.client.tile.renderer

import de.johni0702.minecraft.betterportals.client.render.FramedPortalRenderer
import de.johni0702.minecraft.betterportals.impl.mekanism.common.tile.LinkedTeleporterPortal
import de.johni0702.minecraft.betterportals.impl.mekanism.common.tile.TileEntityBetterTeleporter
import de.johni0702.minecraft.view.client.render.RenderPass
import mekanism.client.render.tileentity.RenderTeleporter
import mekanism.common.tile.TileEntityTeleporter
import net.minecraft.client.shader.Framebuffer
import net.minecraft.util.math.Vec3d

class RenderBetterTeleporter : RenderTeleporter() {
    private var renderingSkipped = true
    private val portalRenderer = object : FramedPortalRenderer<LinkedTeleporterPortal>() {
        override fun renderPortal(portal: LinkedTeleporterPortal, pos: Vec3d, framebuffer: Framebuffer?, renderPass: RenderPass) {
            super.renderPortal(portal, pos, framebuffer, renderPass)
            renderingSkipped = false
        }
    }

    override fun render(tileEntity: TileEntityTeleporter, x: Double, y: Double, z: Double, partialTick: Float, destroyStage: Int, alpha: Float) {
        if (tileEntity is TileEntityBetterTeleporter && tileEntity.active) {
            val agent = tileEntity.agent
            if (agent != null) {
                renderingSkipped = true
                portalRenderer.render(agent.portal, Vec3d(x + 0.5, y + 1.5, z + 0.5), partialTick)
                if (renderingSkipped) {
                    return
                }
            }
        }
        super.render(tileEntity, x, y, z, partialTick, destroyStage, alpha)
    }
}