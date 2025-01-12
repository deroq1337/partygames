package com.github.deroq1337.partygames.core.data.game.config;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;

@AllArgsConstructor
public abstract class YamlConfig {

    private final @NotNull DumperOptions dumperOptions;
    private final @NotNull LoaderOptions loaderOptions;
    private final @NotNull Representer representer;

    protected @NotNull File file;

    public YamlConfig() {
        this.dumperOptions = new DumperOptions();
        dumperOptions.setPrettyFlow(true);
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setExplicitStart(true);

        this.loaderOptions = new LoaderOptions();
        loaderOptions.setTagInspector(tag -> true);

        this.representer = new Representer(dumperOptions);
        representer.getPropertyUtils().setSkipMissingProperties(true);
    }

    public YamlConfig(@NotNull File file) {
        this();
        this.file = file;
    }

    public boolean delete() {
        return file.delete();
    }

    public boolean exists() {
        return file.exists();
    }

    public void save() throws IOException {
        Yaml yaml = new Yaml(representer, dumperOptions);
        try (FileWriter writer = new FileWriter(file)) {
            yaml.dump(this, writer);
        }
    }

    public <T extends YamlConfig> @NotNull T load(Class<T> clazz) {
        Yaml yaml = new Yaml(new Constructor(clazz, loaderOptions), representer, dumperOptions);
        try (FileReader reader = new FileReader(file)) {
            T config = yaml.loadAs(reader, clazz);
            config.file = file;
            return config;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
